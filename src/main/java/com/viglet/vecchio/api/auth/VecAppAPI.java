package com.viglet.vecchio.api.auth;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;

import com.viglet.vecchio.persistence.model.VecApp;
import com.viglet.vecchio.persistence.service.VecAppService;

@Path("/app")
public class VecAppAPI {
	VecAppService vecAppService = new VecAppService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<VecApp> list() throws Exception {
		return vecAppService.listAll();
	}
	
	@Path("/{appId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public VecApp edit(@PathParam("appId") int id) throws Exception {
		return vecAppService.getApp(id);
	}
	
	@Path("/{appId}/gen_key")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public VecApp genKey(@PathParam("appId") int id) throws Exception {
		VecApp vecAppEdit = vecAppService.getApp(id);
		vecAppEdit.setApiKey((new MD5Generator()).generateValue());
		vecAppEdit.setApiSecret((new MD5Generator()).generateValue());
		vecAppService.save(vecAppEdit);
		return vecAppEdit;
	}
	
	@Path("/{appId}/gen_token")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public VecApp genToken(@PathParam("appId") int id) throws Exception {
		MD5Generator tokenSecret = new MD5Generator();
		OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(tokenSecret);
		VecApp vecAppEdit = vecAppService.getApp(id);
		vecAppEdit.setAccessToken(tokenSecret.generateValue());
		vecAppEdit.setAccessTokenSecret(oauthIssuerImpl.accessToken());
		vecAppService.save(vecAppEdit);
		return vecAppEdit;
	}
	
	@Path("/{appId}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public VecApp update(@PathParam("appId") int id, VecApp vecApp) throws Exception {
		VecApp vecAppEdit = vecAppService.getApp(id);
		vecAppEdit.setName(vecApp.getName());
		vecAppEdit.setDescription(vecApp.getDescription());
		vecAppEdit.setCallbackURL(vecApp.getCallbackURL());
		vecAppService.save(vecAppEdit);
		return vecAppEdit;
	}

	@Path("/{appId}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public boolean delete(@PathParam("appId") int id) throws Exception {
		return vecAppService.deleteApp(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(VecApp vecApp) throws Exception {
		MD5Generator tokenSecret = new MD5Generator();
		OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(tokenSecret);
		vecApp.setApiKey((new MD5Generator()).generateValue());
		vecApp.setApiSecret((new MD5Generator()).generateValue());
		vecApp.setAccessToken(tokenSecret.generateValue());
		vecApp.setAccessTokenSecret(oauthIssuerImpl.accessToken());
		vecAppService.save(vecApp);
		String result = "App saved : " + vecApp;
		return Response.status(201).entity(result).build();

	}
}
