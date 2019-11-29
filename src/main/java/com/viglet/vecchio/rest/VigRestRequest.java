package com.viglet.vecchio.rest;

import java.util.regex.Pattern;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.regex.Matcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.viglet.vecchio.api.oauth2.demo.TestContent;
import com.viglet.vecchio.persistence.model.VecMapping;
import com.viglet.vecchio.persistence.repository.VecMappingRepository;
import com.viglet.vecchio.persistence.service.VecAppService;
import com.viglet.vecchio.proxy.VigProxy;

@Component
public class VigRestRequest {
	@Autowired
	private VecMappingRepository vecMappingRepository;
	@Autowired
	private VigProxy vigProxy;
	// Accommodate two requests, one for all resources, another for a specific
	// resource
	private Pattern regExIdPattern = Pattern.compile("/resource/([0-9]*)");

	private Integer id;

	public void run(String pathInfo, OutputStream ops, HttpServletRequest request)
			throws ServletException, OAuthSystemException {

		try {
			for (VecMapping vecMapping : vecMappingRepository.findAll()) {

				if (Pattern.compile(vecMapping.getPattern()).matcher(pathInfo).matches()) {
					// Match Pattern!

					try {

						// Make the OAuth Request out of this request
						OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request,
								ParameterStyle.HEADER);

						// Get the access token
						String accessToken = oauthRequest.getAccessToken();

						// Validate the access token
						VecAppService vecAppService = new VecAppService();
						if (vecAppService.getAppByAccessToken(accessToken) == null) {

							// Return the OAuth error message
							OAuthResponse oauthResponse = OAuthRSResponse
									.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
									.setRealm(TestContent.RESOURCE_SERVER_NAME)
									.setError(OAuthError.ResourceResponse.INVALID_TOKEN).buildHeaderMessage();

							// return
							// Response.status(Response.Status.UNAUTHORIZED).build();
							return;
							/*
							 * return Response.status(Response.Status.UNAUTHORIZED)
							 * .header(OAuth.HeaderType.WWW_AUTHENTICATE,
							 * oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE)) .build();
							 */
						}

						// Return the resource
						vigProxy.run(new URL(vecMapping.getUrl()), ops, vecMapping);
						return;
						// return Response.status(Response.Status.OK).entity(accessToken).build();

					} catch (OAuthProblemException e) {
						// Check if the error code has been set
						String errorCode = e.getError();
						if (OAuthUtils.isEmpty(errorCode)) {

							// Return the OAuth error message
							OAuthResponse oauthResponse = OAuthRSResponse
									.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
									.setRealm(TestContent.RESOURCE_SERVER_NAME).buildHeaderMessage();

							// If no error code then return a standard 401
							// Unauthorized response
							return;
							/*
							 * return Response.status(Response.Status.UNAUTHORIZED)
							 * .header(OAuth.HeaderType.WWW_AUTHENTICATE,
							 * oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE)) .build();
							 */
						}

						OAuthResponse oauthResponse = OAuthRSResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
								.setRealm(TestContent.RESOURCE_SERVER_NAME).setError(e.getError())
								.setErrorDescription(e.getDescription()).setErrorUri(e.getDescription())
								.buildHeaderMessage();
						return;
						/*
						 * return Response.status(Response.Status.BAD_REQUEST).header(OAuth.HeaderType.
						 * WWW_AUTHENTICATE,
						 * oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE)).build();
						 */
					}

					/// END Match

				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// regex parse pathInfo
		Matcher matcher;

		// Check for ID case first, since the All pattern would also match
		matcher = regExIdPattern.matcher(pathInfo);
		if (matcher.find()) {
			id = Integer.parseInt(matcher.group(1));
			return;
		}

		throw new ServletException("Invalid URI");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}