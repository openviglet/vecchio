package com.viglet.vecchio.persistence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.viglet.vecchio.persistence.model.VecApp;
import com.viglet.vecchio.persistence.model.oauth.VecOAuthAccessToken;
import com.viglet.vecchio.persistence.repository.VecAppRepository;
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
		
			VecOAuthAccessToken vecOAuthAccessToken = vecOAuthAccessTokenRepository.findByAccessToken(accessToken);
			if (vecOAuthAccessToken != null) {
				vecApp = this.getAppByClientId(vecOAuthAccessToken.getId().getClientId());
			}
		}
		return vecApp;

	}

	public VecApp getAppByClientId(String clientId) {
		return vecAppRepository.findByApiKey(clientId);
	}
}
