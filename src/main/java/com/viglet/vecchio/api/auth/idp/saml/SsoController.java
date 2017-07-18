package com.viglet.vecchio.api.auth.idp.saml;

import com.viglet.vecchio.api.auth.idp.IdpConfiguration;
import com.viglet.vecchio.security.saml.SAMLAttribute;
import com.viglet.vecchio.security.saml.SAMLPrincipal;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.NameIDType;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.signature.SignatureException;
import org.opensaml.xml.validation.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

/*@Controller*/
public class SsoController {

	private SAMLMessageHandler samlMessageHandler;
	private IdpConfiguration idpConfiguration;

	@Path("/SingleSignOnService")
	@GET
	public void singleSignOnServiceGet(HttpServletRequest request, HttpServletResponse response)
		/*	Authentication authentication) */
			throws IOException, MarshallingException, SignatureException, MessageEncodingException, ValidationException,
			SecurityException, MessageDecodingException, MetadataProviderException {
		doSSO(request, response, false);
	}

	@Path("/SingleSignOnService")
	@POST
	public void singleSignOnServicePost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, MarshallingException, SignatureException, MessageEncodingException, ValidationException,
			SecurityException, MessageDecodingException, MetadataProviderException {
		doSSO(request, response, true);
	}

	private void doSSO(HttpServletRequest request, HttpServletResponse response,
			boolean postRequest) throws ValidationException, SecurityException, MessageDecodingException,
			MarshallingException, SignatureException, MessageEncodingException, MetadataProviderException {
		SAMLMessageContext messageContext = samlMessageHandler.extractSAMLMessageContext(request, response,
				postRequest);
		AuthnRequest authnRequest = (AuthnRequest) messageContext.getInboundSAMLMessage();

		String assertionConsumerServiceURL = idpConfiguration.getAcsEndpoint() != null
				? idpConfiguration.getAcsEndpoint() : authnRequest.getAssertionConsumerServiceURL();

	/*	SAMLPrincipal principal = new SAMLPrincipal(authentication.getName(), NameIDType.UNSPECIFIED,
				attributes(authentication.getName()), authnRequest.getIssuer().getValue(), authnRequest.getID(),
				assertionConsumerServiceURL, messageContext.getRelayState());

		samlMessageHandler.sendAuthnResponse(principal, response); */
	}

	private List<SAMLAttribute> attributes(String uid) {
		return idpConfiguration.getAttributes().entrySet().stream()
				.map(entry -> entry.getKey().equals("urn:mace:dir:attribute-def:uid")
						? new SAMLAttribute(entry.getKey(), singletonList(uid))
						: new SAMLAttribute(entry.getKey(), entry.getValue()))
				.collect(toList());
	}

}
