package com.viglet.vecchio.api.oauth2;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;

/**
 * This is the client server's redirect end point. This end point will live in
 * the client's server backend, not in this OAuth service. This is end point is
 * created so that we can see the Authorisation code that is sent to the client
 * as a part of the redirect url.
 */

@RestController
@RequestMapping("/oauth/redirect")
@Api(value = "/redirect", tags = "Redirect", description = "Redirect")
public class RedirectUriEndpoint {

	@GetMapping
	@ResponseBody
	public ResponseEntity<String> authorize(HttpServletRequest request)
			throws URISyntaxException, OAuthSystemException {

		System.out.println("Request Params:" + request.getParameterMap());

		return ResponseEntity.ok().build();
	}

}
