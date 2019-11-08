package com.viglet.vecchio.api.oauth2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viglet.vecchio.api.oauth2.demo.TestContent;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/token")
@Api(value = "/token", tags = "Token", description = "Token")
public class TokenEndpoint {

	@PostMapping(consumes = "application/x-www-form-urlencoded", produces = "application/json")
	public ResponseEntity<String> authorize(HttpServletRequest request) throws OAuthSystemException {

		OAuthTokenRequest oauthRequest = null;

		OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());

		try {
			oauthRequest = new OAuthTokenRequest(request);

			// check if clientid is not valid
			if (!TestContent.CLIENT_ID.equals(oauthRequest.getParam(OAuth.OAUTH_CLIENT_ID))) {
				OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
						.setError(OAuthError.TokenResponse.INVALID_CLIENT).setErrorDescription("client_id not found")
						.buildJSONMessage();
				return ResponseEntity.status(response.getResponseStatus()).body(response.getBody());
			}

			// do checking for different grant types
			if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.AUTHORIZATION_CODE.toString())) {
				if (!TestContent.AUTHORIZATION_CODE.equals(oauthRequest.getParam(OAuth.OAUTH_CODE))) {
					OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
							.setError(OAuthError.TokenResponse.INVALID_GRANT)
							.setErrorDescription("invalid authorization code").buildJSONMessage();
					return ResponseEntity.status(response.getResponseStatus()).body(response.getBody());
				}
			} else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.PASSWORD.toString())) {
				if (!TestContent.PASSWORD.equals(oauthRequest.getPassword())
						|| !TestContent.USERNAME.equals(oauthRequest.getUsername())) {
					OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
							.setError(OAuthError.TokenResponse.INVALID_GRANT)
							.setErrorDescription("invalid username or password").buildJSONMessage();
					return ResponseEntity.status(response.getResponseStatus()).body(response.getBody());
				}
			} else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.REFRESH_TOKEN.toString())) {
				OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
						.setError(OAuthError.TokenResponse.INVALID_GRANT)
						.setErrorDescription("invalid username or password").buildJSONMessage();
				return ResponseEntity.status(response.getResponseStatus()).body(response.getBody());
			}

			OAuthResponse response = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
					.setAccessToken(oauthIssuerImpl.accessToken()).setExpiresIn("3600").buildJSONMessage();

			return ResponseEntity.status(response.getResponseStatus()).body(response.getBody());
		} catch (OAuthProblemException e) {
			OAuthResponse res = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e)
					.buildJSONMessage();
			return ResponseEntity.status(res.getResponseStatus()).body(res.getBody());
		}
	}

	@GetMapping(consumes = "application/x-www-form-urlencoded", produces = "application/json")
	public ResponseEntity<String> authorizeGet(HttpServletRequest request) throws OAuthSystemException {
		OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());

		OAuthResponse response = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
				.setAccessToken(oauthIssuerImpl.accessToken()).setExpiresIn("3600").buildJSONMessage();

		return ResponseEntity.status(response.getResponseStatus()).body(response.getBody());
	}

}