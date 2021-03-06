package com.viglet.vecchio.persistence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.viglet.vecchio.persistence.model.app.VecApp;
import com.viglet.vecchio.persistence.model.oauth.VecOAuthAccessToken;
import com.viglet.vecchio.persistence.repository.app.VecAppRepository;
import com.viglet.vecchio.persistence.repository.oauth.VecOAuthAccessTokenRepository;

@Component
public class VecAppService {

	@Autowired
	VecAppRepository vecAppRepository;
	@Autowired
	VecOAuthAccessTokenRepository vecOAuthAccessTokenRepository;
	
	public VecApp getAppByAccessToken(String accessToken) {

		VecApp vecApp = vecAppRepository.findByAccessToken(accessToken);
		if (vecApp == null) {
		
			VecOAuthAccessToken vecOAuthAccessToken = vecOAuthAccessTokenRepository.findById_AccessToken(accessToken);
			if (vecOAuthAccessToken != null) {
				vecApp = vecAppRepository.findByApiKey(vecOAuthAccessToken.getId().getClientId());
			}
		}
		return vecApp;

	}
}
