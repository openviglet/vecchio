package com.viglet.vecchio.api.context;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.viglet.vecchio.rest.VigRestMap;
import com.viglet.vecchio.rest.VigRestMaps;

@Path("/mapping")
public class VecMappingAPI {

	@GET
	@Produces("application/json")
	public Response list() throws JSONException {
		VigRestMaps vigRestMaps = new VigRestMaps();
		JSONArray mappping = new JSONArray();
		for (VigRestMap vigRestMap : vigRestMaps.getVigRestMaps()) {
			JSONObject vigRestMapJSON = new JSONObject();
			vigRestMapJSON.put("pattern", vigRestMap.getPattern());
			vigRestMapJSON.put("url", vigRestMap.getUrl());
			mappping.put(vigRestMapJSON);
		}
		return Response.status(200).entity(mappping.toString()).build();
	}
}
