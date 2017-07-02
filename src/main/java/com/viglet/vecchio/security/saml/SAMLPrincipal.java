package com.viglet.vecchio.security.saml;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

public class SAMLPrincipal implements Principal {

  private String serviceProviderEntityID;
  private String requestID;
  private String assertionConsumerServiceURL;
  private String relayState;

  private final List<SAMLAttribute> attributes = new ArrayList<>();

  private String nameID;
  private String nameIDType;

  public SAMLPrincipal(String nameID, String nameIDType, List<SAMLAttribute> attributes) {
    this.nameID = nameID;
    this.nameIDType = nameIDType;
    this.attributes.addAll(attributes);
  }

  public SAMLPrincipal(String nameID, String nameIDType, List<SAMLAttribute> attributes, String serviceProviderEntityID, String requestID, String assertionConsumerServiceURL, String relayState) {
    this(nameID, nameIDType, attributes);
    this.serviceProviderEntityID = serviceProviderEntityID;
    this.requestID = requestID;
    this.assertionConsumerServiceURL = assertionConsumerServiceURL;
    this.relayState = relayState;
  }

  @Override
  public String getName() {
    return nameID;
  }

public String getServiceProviderEntityID() {
	return serviceProviderEntityID;
}

public void setServiceProviderEntityID(String serviceProviderEntityID) {
	this.serviceProviderEntityID = serviceProviderEntityID;
}

public String getRequestID() {
	return requestID;
}

public void setRequestID(String requestID) {
	this.requestID = requestID;
}

public String getAssertionConsumerServiceURL() {
	return assertionConsumerServiceURL;
}

public void setAssertionConsumerServiceURL(String assertionConsumerServiceURL) {
	this.assertionConsumerServiceURL = assertionConsumerServiceURL;
}

public String getRelayState() {
	return relayState;
}

public void setRelayState(String relayState) {
	this.relayState = relayState;
}

public String getNameID() {
	return nameID;
}

public void setNameID(String nameID) {
	this.nameID = nameID;
}

public String getNameIDType() {
	return nameIDType;
}

public void setNameIDType(String nameIDType) {
	this.nameIDType = nameIDType;
}

public List<SAMLAttribute> getAttributes() {
	return attributes;
}
}
