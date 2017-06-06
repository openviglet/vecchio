package com.viglet.vecchio.api.context;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.viglet.vecchio.persistence.model.VecMapping;
import com.viglet.vecchio.persistence.service.VecMappingService;

@Path("/mapping")
public class VecMappingAPI {
	VecMappingService vecMappingService = new VecMappingService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<VecMapping> list() throws Exception {
		return vecMappingService.listAll();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(VecMapping vecMapping) throws Exception {
		vecMappingService.save(vecMapping);
		String result = "Mapping saved : " + vecMapping;
		return Response.status(201).entity(result).build();

	}
}
