package com.viglet.vecchio.api.filter;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

public class CORSFilter implements ContainerResponseFilter {

	@Override
	public ContainerResponse filter(ContainerRequest containerRequest, ContainerResponse containerResponse) {
		ResponseBuilder responseBuilder = Response.fromResponse(containerResponse.getResponse());

		responseBuilder.header("Access-Control-Allow-Origin", "*");
		responseBuilder.header("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
		responseBuilder.header("Access-Control-Max-Age", "86400");

		containerResponse.setResponse(responseBuilder.build());

		return containerResponse;
	}
}