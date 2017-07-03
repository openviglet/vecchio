package com.viglet.vecchio.api.auth.idp;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.util.stream.Collectors.toList;

public class IdpController {
	static final Logger logger = LogManager.getLogger(IdpController.class.getName());

	@Path("/attributes")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void setAttributes(Map<String, List<String>> attributes) {
		logger.debug("Request to replace all attributes {}", attributes);
		// configuration().setAttributes(attributes);
	}

	@Path("/attributes/{name:.+}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void setAttribute(@PathParam("name:.+") String name, List<String> values) {
		logger.debug("Request to set attribute {} to {}", name, values);
		// configuration().getAttributes().put(name, values);
	}

	@Path("/attributes/{name:.+}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public void removeAttribute(@PathParam("name:.+") String name) {
		logger.debug("Request to remove attribute {}", name);
		// configuration().getAttributes().remove(name);
	}

	@Path("/users")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void addUser(User user) {
		logger.debug("Request to add user {}", user);
		// configuration().getUsers().add(new
		// UsernamePasswordAuthenticationToken(user.getName(),
		// user.getPassword(),
		// user.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(toList())));
	}

	@Path("authmethod")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void setAuthenticationMethod(String authenticationMethod) {
		logger.debug("Request to set auth method to {}", authenticationMethod);
		// configuration().setAuthenticationMethod(AuthenticationMethod.valueOf(authenticationMethod));
	}

	@Path("/acsendpoint")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void setAcsEndpoint(String acsEndpoint) {
		logger.debug("Request to set Assertion Consumer Service Endpoint to {}", acsEndpoint);
		// configuration().setAcsEndpoint(acsEndpoint);
	}

	/*
	 * private IdpConfiguration configuration() { return
	 * IdpConfiguration.class.cast(super.configuration); }
	 */

}
