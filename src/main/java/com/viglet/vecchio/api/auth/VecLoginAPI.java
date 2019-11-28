package com.viglet.vecchio.api.auth;

import java.net.URI;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viglet.vecchio.persistence.model.VecApp;
import com.viglet.vecchio.persistence.model.auth.VecUser;
import com.viglet.vecchio.persistence.model.oauth.VecOAuthAccessToken;
import com.viglet.vecchio.persistence.model.oauth.VecOAuthAccessTokenPK;
import com.viglet.vecchio.persistence.model.oauth.VecOAuthAuthorizationCode;
import com.viglet.vecchio.persistence.model.oauth.VecOAuthAuthorizationCodePK;
import com.viglet.vecchio.persistence.repository.auth.VecUserRepository;
import com.viglet.vecchio.persistence.repository.oauth.VecOAuthAccessTokenRepository;
import com.viglet.vecchio.persistence.repository.oauth.VecOAuthAuthorizationCodeRepository;
import com.viglet.vecchio.persistence.service.VecAppService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/login")
@Api(value = "/login", tags = "Login", description = "Login")
public class VecLoginAPI {
	@Autowired
	private VecAppService vecAppService;
	@Autowired
	private VecUserRepository vecUserRepository;
	@Autowired
	private VecOAuthAuthorizationCodeRepository vecOAuthAuthorizationCodeRepository;
	@Autowired
	private VecOAuthAccessTokenRepository vecOAuthAccessTokenRepository;
	ObjectMapper mapper = new ObjectMapper();

	@PostMapping
	public ResponseEntity<String> authentication(HttpServletRequest request, @RequestParam("j_username") String username,
			@RequestParam("j_password") String password) throws Exception {
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
			VecUser vecUser = vecUserRepository.findByUsernameAndPassword(username, password);
			
			if (clientId == null) {

				return ResponseEntity.status(HttpStatus.FOUND).body("OAuth callback url needs client_id");
			} else {
				VecApp vecApp = vecAppService.getAppByClientId(clientId);
				if (vecApp == null) {
					return ResponseEntity.status(HttpStatus.FOUND).body("OAuth callback url needs valid client_id");
				}
				
				
				if (vecUser == null) {
					String loginPage = request.getRequestURL().toString().replaceAll("/api/login", "/login");

					URI loginURI = new URI(loginPage + "?response_type=" + responseType + "&client_id=" + clientId
							+ "&redirect_uri=" + redirectURI + "&error=invalid_username_password&username=" + username);

					return ResponseEntity.status(HttpStatus.FOUND).location(loginURI).build();

				}
			}

			if (responseType.equals(ResponseType.CODE.toString())) {

				VecOAuthAuthorizationCode vecOAuthAuthorizationCode = vecOAuthAuthorizationCodeRepository.findByClientId(clientId);
				if (vecOAuthAuthorizationCode != null) {
					vecOAuthAuthorizationCodeRepository.delete(vecOAuthAuthorizationCode);
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
				vecOAuthAuthorizationCode.setScope("email");
				vecOAuthAuthorizationCode.setUserId("userId");

				vecOAuthAuthorizationCodeRepository.saveAndFlush(vecOAuthAuthorizationCode);
				builder.setCode(id.getAuthorizationCode());
			}
			if (responseType.equals(ResponseType.TOKEN.toString())) {
				VecOAuthAccessToken vecOAuthAccessToken = vecOAuthAccessTokenRepository.findByClientId(clientId);						
				if (vecOAuthAccessToken != null) {
					vecOAuthAccessTokenRepository.delete(vecOAuthAccessToken);
				}
				vecOAuthAccessToken = new VecOAuthAccessToken();
				VecOAuthAccessTokenPK id = new VecOAuthAccessTokenPK();
				id.setAccessToken(oauthIssuerImpl.accessToken());
				id.setClientId(clientId);
				
				Date expires = new Date();
				vecOAuthAccessToken.setId(id);
				vecOAuthAccessToken.setExpires(expires);				
				vecOAuthAccessToken.setScope("email");
				vecOAuthAccessToken.setVecUser(vecUser);
				
				vecOAuthAccessTokenRepository.saveAndFlush(vecOAuthAccessToken);
				
				builder.setAccessToken(id.getAccessToken());
				builder.setExpiresIn(3600l);
			}

			final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();

			URI url = new URI(response.getLocationUri());

			return ResponseEntity.status(response.getResponseStatus()).location(url).build();
		} catch (OAuthProblemException e) {
			String redirectUri = e.getRedirectUri();

			if (OAuthUtils.isEmpty(redirectUri)) {
				return ResponseEntity.status(HttpStatus.FOUND).body("OAuth callback url needs to be provided by client!!!");
			}
			final OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND).error(e)
					.location(redirectUri).buildQueryMessage();
			final URI location = new URI(response.getLocationUri());
			return ResponseEntity.status(HttpStatus.FOUND).location(location).build();
		}
	}
}
