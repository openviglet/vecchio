package com.viglet.vecchio.api.oauth2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;

import com.viglet.vecchio.api.oauth2.demo.TestContent;
import com.viglet.vecchio.persistence.model.VecOAuthAccessToken;
import com.viglet.vecchio.persistence.service.VecOAuthAccessTokenService;

@Path("/tokeninfo")
public class VecTokenInfo {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@Context HttpServletRequest request) throws OAuthSystemException {

		VecOAuthAccessTokenService vecOAuthAccessTokenService = new VecOAuthAccessTokenService();
		try {

			// Make the OAuth Request out of this request
			OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);

			// Get the access token
			String accessToken = oauthRequest.getAccessToken();

			VecOAuthAccessToken vecOAuthAccessToken = vecOAuthAccessTokenService.getAccessToken(accessToken);

			// Validate the access token
			if (vecOAuthAccessToken == null) {

				// Return the OAuth error message
				OAuthResponse oauthResponse = OAuthRSResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
						.setRealm(TestContent.RESOURCE_SERVER_NAME).setError(OAuthError.ResourceResponse.INVALID_TOKEN)
						.buildHeaderMessage();

				return Response.status(Response.Status.UNAUTHORIZED).header(OAuth.HeaderType.WWW_AUTHENTICATE,
						oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE)).build();

			}

			// Return the resource
			return Response.status(Response.Status.OK).entity(vecOAuthAccessToken).build();

		} catch (OAuthProblemException e) {
			// Check if the error code has been set
			String errorCode = e.getError();
			if (OAuthUtils.isEmpty(errorCode)) {

				// Return the OAuth error message
				OAuthResponse oauthResponse = OAuthRSResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
						.setRealm(TestContent.RESOURCE_SERVER_NAME).buildHeaderMessage();

				// If no error code then return a standard 401 Unauthorized
				// response
				return Response.status(Response.Status.UNAUTHORIZED).header(OAuth.HeaderType.WWW_AUTHENTICATE,
						oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE)).build();
			}

			OAuthResponse oauthResponse = OAuthRSResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
					.setRealm(TestContent.RESOURCE_SERVER_NAME).setError(e.getError())
					.setErrorDescription(e.getDescription()).setErrorUri(e.getUri()).buildHeaderMessage();

			return Response.status(Response.Status.BAD_REQUEST).header(OAuth.HeaderType.WWW_AUTHENTICATE,
					oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE)).build();
		}

	}
}