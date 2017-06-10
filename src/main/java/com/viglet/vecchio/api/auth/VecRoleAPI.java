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

import com.viglet.vecchio.persistence.model.VecRole;
import com.viglet.vecchio.persistence.service.VecRoleService;

@Path("/role")
public class VecRoleAPI {
	VecRoleService vecRoleService = new VecRoleService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<VecRole> list() throws Exception {
		return vecRoleService.listAll();
	}
	
	@Path("/{roleId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public VecRole edit(@PathParam("userId") int id) throws Exception {
		return vecRoleService.getRole(id);
	}
	
	@Path("/{roleId}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public VecRole update(@PathParam("userId") int id, VecRole vecRole) throws Exception {
		VecRole vecRoleEdit = vecRoleService.getRole(id);
		vecRoleEdit.setName(vecRole.getName());
		vecRoleEdit.setDescription(vecRole.getDescription());
		vecRoleService.save(vecRoleEdit);
		return vecRoleEdit;
	}

	@Path("/{roleId}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public boolean delete(@PathParam("roleId") int id) throws Exception {
		return vecRoleService.deleteRole(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(VecRole vecRole) throws Exception {
		vecRoleService.save(vecRole);
		String result = "Role saved : " + vecRole;
		return Response.status(201).entity(result).build();

	}
}
