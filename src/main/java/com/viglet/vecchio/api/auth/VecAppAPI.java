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
	
	@Path("/{appId}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public VecApp update(@PathParam("appId") int id, VecApp vecApp) throws Exception {
		VecApp vecAppEdit = vecAppService.getApp(id);
		vecAppEdit.setName(vecApp.getName());
		vecAppEdit.setDescription(vecApp.getDescription());
		vecAppEdit.setApiKey(vecApp.getApiKey());
		vecAppEdit.setApiSecret(vecApp.getApiSecret());
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
		vecAppService.save(vecApp);
		String result = "App saved : " + vecApp;
		return Response.status(201).entity(result).build();

	}
}
