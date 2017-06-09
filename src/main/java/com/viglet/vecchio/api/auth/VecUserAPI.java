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

import com.viglet.vecchio.persistence.model.VecMapping;
import com.viglet.vecchio.persistence.model.VecUser;
import com.viglet.vecchio.persistence.service.VecUserService;

@Path("/user")
public class VecUserAPI {
	VecUserService vecUserService = new VecUserService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<VecUser> list() throws Exception {
		return vecUserService.listAll();
	}
	
	@Path("/{userId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public VecUser edit(@PathParam("userId") int id) throws Exception {
		return vecUserService.getUser(id);
	}
	
	@Path("/{userId}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public VecUser update(@PathParam("userId") int id, VecUser vecUser) throws Exception {
		VecUser vecUserEdit = vecUserService.getUser(id);
		vecUserEdit.setConfirmEmail(vecUser.getConfirmEmail());
		vecUserEdit.setEmail(vecUser.getEmail());
		vecUserEdit.setFirstName(vecUser.getFirstName());
		vecUserEdit.setLastLogin(vecUser.getLastLogin());
		vecUserEdit.setLastName(vecUser.getLastName());
		vecUserEdit.setLoginTimes(vecUser.getLoginTimes());
		vecUserEdit.setPassword(vecUser.getPassword());
		vecUserEdit.setRealm(vecUser.getRealm());
		vecUserEdit.setRecoverPassword(vecUser.getRecoverPassword());
		vecUserEdit.setUsername(vecUser.getUsername());

		vecUserService.save(vecUserEdit);
		return vecUserEdit;
	}

	@Path("/{userId}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public boolean delete(@PathParam("userId") int id) throws Exception {
		return vecUserService.deleteUser(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(VecUser vecUser) throws Exception {
		vecUserService.save(vecUser);
		String result = "User saved : " + vecUser;
		return Response.status(201).entity(result).build();

	}
}
