package com.viglet.vecchio.api.oauth2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.viglet.vecchio.persistence.model.app.VecApp;
import com.viglet.vecchio.persistence.model.oauth.VecOAuthAccessToken;
import com.viglet.vecchio.persistence.model.oauth.VecOAuthAccessTokenPK;
import com.viglet.vecchio.persistence.model.oauth.VecOAuthAuthorizationCode;
import com.viglet.vecchio.persistence.model.oauth.VecOAuthAuthorizationCodePK;
import com.viglet.vecchio.persistence.repository.oauth.VecOAuthAccessTokenRepository;
import com.viglet.vecchio.persistence.repository.oauth.VecOAuthAuthorizationCodeRepository;
import com.viglet.vecchio.persistence.service.VecAppService;

import io.swagger.annotations.Api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

@RestController
@RequestMapping("/authorize")
@Api(value = "/authorize", tags = "Authorize", description = "Authorize")
public class AuthEndpoint {
	@Autowired
	private VecOAuthAuthorizationCodeRepository vecOAuthAuthorizationCodeRepository;
	@Autowired
	private VecOAuthAccessTokenRepository vecOAuthAccessTokenRepository;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<String> authorize(HttpServletRequest request) throws URISyntaxException, OAuthSystemException {

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
				return ResponseEntity.status(HttpStatus.FOUND).body("OAuth callback url needs client_id");

			} else {
				VecApp vecApp = vecAppService.getAppByClientId(clientId);
				if (vecApp == null) {
					return ResponseEntity.status(HttpStatus.FOUND).body("OAuth callback url needs valid client_id");
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
				vecOAuthAuthorizationCode.setScope(oauthRequest.getParam(OAuth.OAUTH_SCOPE));
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
				return ResponseEntity.status(HttpStatus.FOUND)
						.body("OAuth callback url needs to be provided by client!!!");

			}
			final OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND).error(e)
					.location(redirectUri).buildQueryMessage();
			final URI location = new URI(response.getLocationUri());
			return ResponseEntity.status(HttpStatus.FOUND).location(location).build();
		}
	}

}