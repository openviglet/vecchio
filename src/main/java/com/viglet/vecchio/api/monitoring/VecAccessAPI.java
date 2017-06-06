package com.viglet.vecchio.api.monitoring;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.viglet.vecchio.persistence.model.VecAccess;
import com.viglet.vecchio.persistence.model.VecMapping;
import com.viglet.vecchio.persistence.service.VecAccessService;
import com.viglet.vecchio.persistence.service.VecMappingService;

@Path("/access")
public class VecAccessAPI {
	VecAccessService vecAccessService = new VecAccessService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<VecAccess> list() throws Exception {
		return vecAccessService.listAll();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(VecAccess vecAccess) throws Exception {
		vecAccessService.save(vecAccess);
		String result = "Access saved : " + vecAccess;
		return Response.status(201).entity(result).build();

	}
}