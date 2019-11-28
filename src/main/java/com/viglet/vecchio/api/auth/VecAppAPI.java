package com.viglet.vecchio.api.auth;

import java.util.List;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viglet.vecchio.persistence.model.VecApp;
import com.viglet.vecchio.persistence.repository.VecAppRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/app")
@Api(value = "/app", tags = "App", description = "App")
public class VecAppAPI {

	@Autowired
	private VecAppRepository vecAppRepository;

	@ApiOperation(value = "Show App List")
	@GetMapping
	public List<VecApp> list() {
		return vecAppRepository.findAll();
	}

	@ApiOperation(value = "Show a App")
	@GetMapping("/{id}")
	public VecApp edit(@PathVariable String id) {

		return vecAppRepository.findById(id).get();
	}

	@ApiOperation(value = "Generate Key to App")
	@GetMapping("/{id}/gen_key")
	public VecApp genKey(@PathVariable String id) {
		VecApp vecAppEdit = vecAppRepository.findById(id).get();
		try {
			vecAppEdit.setApiKey((new MD5Generator()).generateValue());
			vecAppEdit.setApiSecret((new MD5Generator()).generateValue());
		} catch (OAuthSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vecAppRepository.save(vecAppEdit);
		return vecAppEdit;
	}

	@ApiOperation(value = "Generate Toket to App")
	@GetMapping("/{id}/gen_token")
	public VecApp genToken(@PathVariable String id) {
		MD5Generator tokenSecret = new MD5Generator();
		OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(tokenSecret);
		VecApp vecAppEdit = vecAppRepository.findById(id).get();
		try {
			vecAppEdit.setAccessToken(tokenSecret.generateValue());
			vecAppEdit.setAccessTokenSecret(oauthIssuerImpl.accessToken());
		} catch (OAuthSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vecAppRepository.save(vecAppEdit);
		return vecAppEdit;
	}

	@ApiOperation(value = "Update a App")
	@PutMapping("/{id}")
	public VecApp update(@PathVariable String id, VecApp vecApp) {
		VecApp vecAppEdit = vecAppRepository.findById(id).get();
		vecAppEdit.setName(vecApp.getName());
		vecAppEdit.setDescription(vecApp.getDescription());
		vecAppEdit.setCallbackURL(vecApp.getCallbackURL());
		vecAppRepository.save(vecAppEdit);
		return vecAppEdit;
	}

	@ApiOperation(value = "Delete a App")
	@DeleteMapping("/{id}")
	public boolean delete(@PathVariable String id) throws Exception {
		vecAppRepository.delete(id);
		return true;
	}

	@ApiOperation(value = "Create a App")
	@PostMapping
	public VecApp add(VecApp vecApp) {
		MD5Generator tokenSecret = new MD5Generator();
		OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(tokenSecret);
		try {
			vecApp.setApiKey((new MD5Generator()).generateValue());
			vecApp.setApiSecret((new MD5Generator()).generateValue());
			vecApp.setAccessToken(tokenSecret.generateValue());
			vecApp.setAccessTokenSecret(oauthIssuerImpl.accessToken());
		} catch (OAuthSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vecAppRepository.save(vecApp);
		return vecApp;

	}
}
