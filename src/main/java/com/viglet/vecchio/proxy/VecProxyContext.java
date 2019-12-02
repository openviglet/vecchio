package com.viglet.vecchio.proxy;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.security.Principal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;

import com.viglet.vecchio.api.oauth2.demo.TestContent;
import com.viglet.vecchio.persistence.model.app.VecApp;
import com.viglet.vecchio.persistence.model.app.VecMapping;
import com.viglet.vecchio.persistence.repository.app.VecMappingRepository;
import com.viglet.vecchio.persistence.service.VecAppService;

@Controller
public class VecProxyContext {
	@Autowired
	private VecMappingRepository vecMappingRepository;
	@Autowired
	private VecProxy vecProxy;
	@Autowired
	private VecAppService vecAppService;
	
	private Pattern regExIdPattern = Pattern.compile("/resource/([0-9]*)");

	@RequestMapping("/proxy/**")
	private void indexAnyRequest(HttpServletRequest request, HttpServletResponse response, final Principal principal) {
		String pathInfo = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		OutputStream ops;
		try {
			ops = response.getOutputStream();
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
						VecApp vecApp = vecAppService.getAppByAccessToken(accessToken);
						if ( vecApp == null) {
							// Return the OAuth error message
							OAuthRSResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
									.setRealm(TestContent.RESOURCE_SERVER_NAME)
									.setError(OAuthError.ResourceResponse.INVALID_TOKEN).buildHeaderMessage();

							return;

						}

						// Return the resource
						vecProxy.run(new URL(vecMapping.getUrl()), ops, vecMapping);
						return;

					} catch (OAuthProblemException e) {
						// Check if the error code has been set
						String errorCode = e.getError();
						if (OAuthUtils.isEmpty(errorCode)) {
							// Return the OAuth error message
							try {
								OAuthRSResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
										.setRealm(TestContent.RESOURCE_SERVER_NAME).buildHeaderMessage();
							} catch (OAuthSystemException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							// If no error code then return a standard 401
							// Unauthorized response
							return;
						}

						try {
							OAuthRSResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
									.setRealm(TestContent.RESOURCE_SERVER_NAME).setError(e.getError())
									.setErrorDescription(e.getDescription()).setErrorUri(e.getDescription())
									.buildHeaderMessage();
						} catch (OAuthSystemException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						return;
					} catch (OAuthSystemException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
			return;
		}
	}
}
