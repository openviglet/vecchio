package com.viglet.vecchio.api.oauth2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;

import com.viglet.vecchio.persistence.model.VecApp;
import com.viglet.vecchio.persistence.model.VecOAuthAuthorizationCode;
import com.viglet.vecchio.persistence.model.VecOAuthAuthorizationCodePK;
import com.viglet.vecchio.persistence.service.VecAppService;
import com.viglet.vecchio.persistence.service.VecOAuthAuthorizationCodeService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

@Path("/authorize")
public class AuthEndpoint {

	@GET
	public Response authorize(@Context HttpServletRequest request) throws URISyntaxException, OAuthSystemException {
		VecOAuthAuthorizationCodeService vecOAuthAuthorizationCodeService = new VecOAuthAuthorizationCodeService();
		OAuthAuthzRequest oauthRequest = null;

		OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());

		try {
			oauthRequest = new OAuthAuthzRequest(request);

			// build response according to response_type
			String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
			String clientId = oauthRequest.getParam(OAuth.OAUTH_CLIENT_ID);
			String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);

			OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse.authorizationResponse(request,
					HttpServletResponse.SC_FOUND);
			VecAppService vecAppService = new VecAppService();

			if (clientId == null) {

				final Response.ResponseBuilder responseBuilder = Response.status(HttpServletResponse.SC_FOUND);
				throw new WebApplicationException(responseBuilder.entity("OAuth callback url needs client_id").build());
			} else {
				VecApp vecApp = vecAppService.getAppByClientId(clientId);
				if (vecApp == null) {
					final Response.ResponseBuilder responseBuilder = Response.status(HttpServletResponse.SC_FOUND);
					throw new WebApplicationException(
							responseBuilder.entity("OAuth callback url needs valid client_id").build());
				}
			}

			if (responseType.equals(ResponseType.CODE.toString())) {

				VecOAuthAuthorizationCode vecOAuthAuthorizationCode = vecOAuthAuthorizationCodeService
						.getAuthCodeByClientId(clientId);
				if (vecOAuthAuthorizationCode != null) {
					vecOAuthAuthorizationCodeService.deletetAuthCode(vecOAuthAuthorizationCode.getId());
				}
				vecOAuthAuthorizationCode = new VecOAuthAuthorizationCode();
				VecOAuthAuthorizationCodePK id = new VecOAuthAuthorizationCodePK();
				id.setAuthorizationCode(oauthIssuerImpl.authorizationCode());
				id.setClientId(clientId);

				Date expires = new Date();
				vecOAuthAuthorizationCode.setId(id);
				vecOAuthAuthorizationCode.setExpires(expires);
				vecOAuthAuthorizationCode.setIdToken("nulo");
				vecOAuthAuthorizationCode.setRedirectUri(oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI));
				vecOAuthAuthorizationCode.setScope(oauthRequest.getParam(OAuth.OAUTH_STATE));
				vecOAuthAuthorizationCode.setUserId("userId");
				
				vecOAuthAuthorizationCodeService.save(vecOAuthAuthorizationCode);
				builder.setCode(id.getAuthorizationCode());
			}
			if (responseType.equals(ResponseType.TOKEN.toString())) {
				builder.setAccessToken(oauthIssuerImpl.accessToken());
				builder.setExpiresIn(3600l);
			}

			final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();
			URI url = new URI(response.getLocationUri());

			return Response.status(response.getResponseStatus()).location(url).build();

		} catch (OAuthProblemException e) {

			final Response.ResponseBuilder responseBuilder = Response.status(HttpServletResponse.SC_FOUND);

			String redirectUri = e.getRedirectUri();

			if (OAuthUtils.isEmpty(redirectUri)) {
				throw new WebApplicationException(
						responseBuilder.entity("OAuth callback url needs to be provided by client!!!").build());
			}
			final OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND).error(e)
					.location(redirectUri).buildQueryMessage();
			final URI location = new URI(response.getLocationUri());
			return responseBuilder.location(location).build();
		}
	}

}