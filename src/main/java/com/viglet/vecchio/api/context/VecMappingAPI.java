package com.viglet.vecchio.api.context;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
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
import com.viglet.vecchio.persistence.service.VecMappingService;

@Path("/mapping")
public class VecMappingAPI {
	VecMappingService vecMappingService = new VecMappingService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<VecMapping> list() throws Exception {
		return vecMappingService.listAll();
	}

	@Path("/{mappingId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public VecMapping edit(@PathParam("mappingId") int id) throws Exception {
		return vecMappingService.getMapping(id);
	}

	@Path("/{mappingId}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public VecMapping update(@PathParam("mappingId") int id, VecMapping vecMapping) throws Exception {
		VecMapping vecMappingEdit = vecMappingService.getMapping(id);
		vecMappingEdit.setPattern(vecMapping.getPattern());
		vecMappingEdit.setUrl(vecMapping.getUrl());
		vecMappingService.save(vecMappingEdit);
		return vecMappingEdit;
	}

	@Path("/{mappingId}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public boolean delete(@PathParam("mappingId") int id) throws Exception {
		return vecMappingService.deleteMapping(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(VecMapping vecMapping) throws Exception {
		vecMappingService.save(vecMapping);
		String result = "Mapping saved : " + vecMapping;
		return Response.status(201).entity(result).build();

	}
}
