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

import com.viglet.vecchio.persistence.model.VecGroup;
import com.viglet.vecchio.persistence.model.VecRole;
import com.viglet.vecchio.persistence.service.VecGroupService;
import com.viglet.vecchio.persistence.service.VecRoleService;

@Path("/group")
public class VecGroupAPI {
	VecGroupService vecGroupService = new VecGroupService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<VecGroup> list() throws Exception {
		return vecGroupService.listAll();
	}
	
	@Path("/{groupId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public VecGroup edit(@PathParam("groupId") int id) throws Exception {
		return vecGroupService.getGroup(id);
	}
	
	@Path("/{groupId}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public VecGroup update(@PathParam("groupId") int id, VecGroup vecGroup) throws Exception {
		VecGroup vecGroupEdit = vecGroupService.getGroup(id);
		vecGroupEdit.setName(vecGroup.getName());
		vecGroupEdit.setDescription(vecGroup.getDescription());
		vecGroupService.save(vecGroupEdit);
		return vecGroupEdit;
	}

	@Path("/{groupId}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public boolean delete(@PathParam("groupId") int id) throws Exception {
		return vecGroupService.deleteGroup(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(VecGroup vecGroup) throws Exception {
		vecGroupService.save(vecGroup);
		String result = "Group saved : " + vecGroup;
		return Response.status(201).entity(result).build();

	}
}
