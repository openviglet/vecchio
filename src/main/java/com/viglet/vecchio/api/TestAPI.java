package com.viglet.vecchio.api;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.viglet.vecchio.persistence.model.VigOAuthClient;

@Path("/test")
public class TestAPI {

	@GET
	@Produces("application/json")
	public Response list() throws JSONException {
		String PERSISTENCE_UNIT_NAME = "vecchio-app";
		EntityManagerFactory factory;

		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
	      VigOAuthClient vigOAuthClient = new VigOAuthClient();
	      vigOAuthClient.setClientId("1");
	      vigOAuthClient.setClientSecret("2");
	      vigOAuthClient.setRedirectUri("3");
	      em.getTransaction().begin();
	      em.persist(vigOAuthClient);
	      em.getTransaction().commit();
	      try {

	      } finally {
	    	  em.close( );
	         factory.close( );
	      }
		JSONArray vigTest = new JSONArray();
		vigTest.put("this a test");
		return Response.status(200).entity(vigTest.toString()).build();
	}
}