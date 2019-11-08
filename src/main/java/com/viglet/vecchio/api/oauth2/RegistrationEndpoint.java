package com.viglet.vecchio.api.oauth2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.ext.dynamicreg.server.request.JSONHttpServletRequestWrapper;
import org.apache.oltu.oauth2.ext.dynamicreg.server.request.OAuthServerRegistrationRequest;
import org.apache.oltu.oauth2.ext.dynamicreg.server.response.OAuthServerRegistrationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viglet.vecchio.api.oauth2.demo.ServerContent;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/register")
@Api(value = "/register", tags = "Register", description = "Register")
public class RegistrationEndpoint {

	@PostMapping
	public ResponseEntity<String> register(HttpServletRequest request) throws OAuthSystemException {

		OAuthServerRegistrationRequest oauthRequest = null;
		try {
			oauthRequest = new OAuthServerRegistrationRequest(new JSONHttpServletRequestWrapper(request));
			oauthRequest.getType();
			oauthRequest.discover();
			oauthRequest.getClientName();
			oauthRequest.getClientUrl();
			oauthRequest.getClientDescription();
			oauthRequest.getRedirectURI();

			OAuthResponse response = OAuthServerRegistrationResponse.status(HttpServletResponse.SC_OK)
					.setClientId(ServerContent.CLIENT_ID).setClientSecret(ServerContent.CLIENT_SECRET)
					.setIssuedAt(ServerContent.ISSUED_AT).setExpiresIn(ServerContent.EXPIRES_IN).buildJSONMessage();
			return ResponseEntity.status(response.getResponseStatus()).body(response.getBody());

		} catch (OAuthProblemException e) {
			OAuthResponse response = OAuthServerRegistrationResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
					.error(e).buildJSONMessage();
			return ResponseEntity.status(response.getResponseStatus()).body(response.getBody());
		}

	}
}