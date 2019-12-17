/*
 * Copyright (C) 2016-2019 Alexandre Oliveira <alexandre.oliveira@viglet.com> 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.viglet.vecchio.onstartup.app;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.viglet.vecchio.persistence.model.app.VecApp;
import com.viglet.vecchio.persistence.repository.app.VecAppRepository;

@Component
public class VecAppOnStartup {
	public final static String SAMPLE_APP_ID = "a9a77b85-0885-43a6-b0f9-6f40d83dfe10";
	@Autowired
	private VecAppRepository vecAppRepository;

	public void createDefaultRows() {

		if (vecAppRepository.findAll().isEmpty()) {
			MD5Generator tokenSecret = new MD5Generator();
			OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(tokenSecret);
			try {
				VecApp vecApp = new VecApp();

				vecApp.setId(SAMPLE_APP_ID);
				vecApp.setName("Sample App");
				vecApp.setDescription("Demonstrate how the app works");
				vecApp.setAccessToken(tokenSecret.generateValue());
				vecApp.setAccessTokenSecret(oauthIssuerImpl.accessToken());
				vecApp.setApiKey((new MD5Generator()).generateValue());
				vecApp.setApiSecret((new MD5Generator()).generateValue());
				vecApp.setCallbackURL("http://example.com");

				vecAppRepository.saveAndFlush(vecApp);
			} catch (OAuthSystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
