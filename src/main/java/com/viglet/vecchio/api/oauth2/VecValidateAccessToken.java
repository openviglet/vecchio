package com.viglet.vecchio.api.oauth2;

import java.io.IOException;

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
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;

import com.viglet.vecchio.api.oauth2.demo.TestContent;
import com.viglet.vecchio.persistence.model.VecApp;
import com.viglet.vecchio.persistence.model.VecOAuthAccessToken;
import com.viglet.vecchio.persistence.service.VecAppService;
import com.viglet.vecchio.persistence.service.VecMappingService;
import com.viglet.vecchio.persistence.service.VecOAuthAccessTokenService;

@Path("/token_validate")
public class VecValidateAccessToken {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@Context HttpServletRequest request) throws OAuthSystemException {

		VecOAuthAccessTokenService vecOAuthAccessTokenService = new VecOAuthAccessTokenService();
		VecMappingService vecMappingService = new VecMappingService();
		String clientContext = request.getHeader("VecContext");
		System.out.println("VecContext: " + clientContext);
		if (vecMappingService.contextExists(clientContext)) {
			try {
				System.out.println("Context exists");

				// Make the OAuth Request out of this request
				OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);

				// Get the access token
				String accessToken = oauthRequest.getAccessToken();

				VecOAuthAccessToken vecOAuthAccessToken = vecOAuthAccessTokenService.getAccessToken(accessToken);

				// Validate the access token
				if (vecOAuthAccessToken == null) {
					System.out.println("Unauthorized");
					// Return the OAuth error message
					OAuthResponse oauthResponse = OAuthRSResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
							.setRealm(TestContent.RESOURCE_SERVER_NAME)
							.setError(OAuthError.ResourceResponse.INVALID_TOKEN).buildHeaderMessage();

					return Response.status(Response.Status.UNAUTHORIZED).header(OAuth.HeaderType.WWW_AUTHENTICATE,
							oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE)).build();

				}

				String[] ignoreFields = { "apiSecret", "accessToken", "accessTokenSecret", "apiKey", "callbackURL" };
				FilterProvider filter = new SimpleFilterProvider().addFilter("vecAppFilter",
						SimpleBeanPropertyFilter.serializeAllExcept(ignoreFields));
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writer(filter).writeValueAsString(vecOAuthAccessToken);

				// Return the resource
				return Response.status(Response.Status.OK).entity(json).build();

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
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return Response.status(Response.Status.BAD_REQUEST).build();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return Response.status(Response.Status.BAD_REQUEST).build();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
		} 
		else {
			return Response.status(Response.Status.OK).build();
		}
	}
}