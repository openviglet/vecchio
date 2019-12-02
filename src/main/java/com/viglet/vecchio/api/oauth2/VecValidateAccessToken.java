package com.viglet.vecchio.api.oauth2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.viglet.vecchio.api.oauth2.demo.TestContent;
import com.viglet.vecchio.persistence.model.oauth.VecOAuthAccessToken;
import com.viglet.vecchio.persistence.repository.app.VecMappingRepository;
import com.viglet.vecchio.persistence.repository.oauth.VecOAuthAccessTokenRepository;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/token_validate")
@Api(value = "/token_validate", tags = "Token Validate", description = "Token Validate")
public class VecValidateAccessToken {
	@Autowired
	private VecOAuthAccessTokenRepository vecOAuthAccessTokenRepository;
	@Autowired
	private VecMappingRepository vecMappingRepository;

	@GetMapping
	public ResponseEntity<String> get(HttpServletRequest request) throws OAuthSystemException {

		String clientContext = request.getHeader("VecContext");
		System.out.println("VecContext: " + clientContext);
		if (vecMappingRepository.existsByPattern(clientContext)) {
			try {
				System.out.println("Context exists");

				// Make the OAuth Request out of this request
				OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);

				// Get the access token
				String accessToken = oauthRequest.getAccessToken();

				VecOAuthAccessToken vecOAuthAccessToken = vecOAuthAccessTokenRepository.findById_AccessToken(accessToken);

				// Validate the access token
				if (vecOAuthAccessToken == null) {
					System.out.println("Unauthorized");
					// Return the OAuth error message
					OAuthResponse oauthResponse = OAuthRSResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
							.setRealm(TestContent.RESOURCE_SERVER_NAME)
							.setError(OAuthError.ResourceResponse.INVALID_TOKEN).buildHeaderMessage();

					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(OAuth.HeaderType.WWW_AUTHENTICATE,
							oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE)).build();

				}

				String[] ignoreFields = { "apiSecret", "accessToken", "accessTokenSecret", "apiKey", "callbackURL" };
				FilterProvider filter = new SimpleFilterProvider().addFilter("vecAppFilter",
						SimpleBeanPropertyFilter.serializeAllExcept(ignoreFields));
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writer(filter).writeValueAsString(vecOAuthAccessToken);

				// Return the resource
				return ResponseEntity.status(HttpStatus.OK).body(json);

			} catch (OAuthProblemException e) {
				// Check if the error code has been set
				String errorCode = e.getError();
				if (OAuthUtils.isEmpty(errorCode)) {

					// Return the OAuth error message
					OAuthResponse oauthResponse = OAuthRSResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
							.setRealm(TestContent.RESOURCE_SERVER_NAME).buildHeaderMessage();

					// If no error code then return a standard 401 Unauthorized
					// response
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(OAuth.HeaderType.WWW_AUTHENTICATE,
							oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE)).build();
				}

				OAuthResponse oauthResponse = OAuthRSResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
						.setRealm(TestContent.RESOURCE_SERVER_NAME).setError(e.getError())
						.setErrorDescription(e.getDescription()).setErrorUri(e.getUri()).buildHeaderMessage();

				return ResponseEntity.status(HttpStatus.BAD_REQUEST).header(OAuth.HeaderType.WWW_AUTHENTICATE,
						oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE)).build();
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return ResponseEntity.badRequest().build();
			}
		} else {
			return ResponseEntity.ok().build();
		}
	}
}