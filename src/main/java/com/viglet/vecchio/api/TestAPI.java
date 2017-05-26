package com.viglet.vecchio.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/test")
public class TestAPI {

	@GET
	@Produces("application/json")
	public Response list() throws JSONException {
		JSONArray vigTest = new JSONArray();
		vigTest.put("this a test");
		return Response.status(200).entity(vigTest.toString()).build();
	}
}