package com.viglet.vecchio.api.auth.idp;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class IdpConfiguration{

  private String defaultEntityId;
  private Map<String, List<String>> attributes = new TreeMap<>();
 // private List<UsernamePasswordAuthenticationToken> users = new ArrayList<>();
  private String acsEndpoint;
  private AuthenticationMethod authenticationMethod;
  private AuthenticationMethod defaultAuthenticationMethod;
  private final String idpPrivateKey = null;
  private final String idpCertificate = null;

 /* @Autowired
  public IdpConfiguration(JKSKeyManager keyManager,
                          @Value("${idp.entity_id}") String defaultEntityId,
                          @Value("${idp.private_key}") String idpPrivateKey,
                          @Value("${idp.certificate}") String idpCertificate,
                          @Value("${idp.auth_method}") String authMethod) {
    super(keyManager);
    this.defaultEntityId = defaultEntityId;
    this.idpPrivateKey = idpPrivateKey;
    this.idpCertificate = idpCertificate;
    this.defaultAuthenticationMethod = AuthenticationMethod.valueOf(authMethod);
    reset();
  }

  @Override
  public void reset() {
    setEntityId(defaultEntityId);
    resetAttributes();
    resetKeyStore(defaultEntityId, idpPrivateKey, idpCertificate);
    resetUsers();
    setAcsEndpoint(null);
    setAuthenticationMethod(this.defaultAuthenticationMethod);
    setSignatureAlgorithm(getDefaultSignatureAlgorithm());
  }

  private void resetUsers() {
    users.clear();
    users.addAll(Arrays.asList(
      new UsernamePasswordAuthenticationToken("admin", "secret", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"),
        new SimpleGrantedAuthority("ROLE_ADMIN"))),
      new UsernamePasswordAuthenticationToken("user", "secret", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")))));
  }
*/
  private void resetAttributes() {
    attributes.clear();
    putAttribute("urn:mace:dir:attribute-def:uid", "john.doe");
    putAttribute("urn:mace:dir:attribute-def:cn", "John Doe");
    putAttribute("urn:mace:dir:attribute-def:givenName", "John");
    putAttribute("urn:mace:dir:attribute-def:sn", "Doe");
    putAttribute("urn:mace:dir:attribute-def:displayName", "John Doe");
    putAttribute("urn:mace:dir:attribute-def:mail", "j.doe@example.com");
    putAttribute("urn:mace:terena.org:attribute-def:schacHomeOrganization", "example.com");
    putAttribute("urn:mace:dir:attribute-def:eduPersonPrincipalName", "j.doe@example.com");
  }

  private void putAttribute(String key, String... values) {
    this.attributes.put(key, Arrays.asList(values));
  }

public String getDefaultEntityId() {
	return defaultEntityId;
}

public void setDefaultEntityId(String defaultEntityId) {
	this.defaultEntityId = defaultEntityId;
}

public Map<String, List<String>> getAttributes() {
	return attributes;
}

public void setAttributes(Map<String, List<String>> attributes) {
	this.attributes = attributes;
}

/*public List<UsernamePasswordAuthenticationToken> getUsers() {
	return users;
}

public void setUsers(List<UsernamePasswordAuthenticationToken> users) {
	this.users = users;
}*/

public String getAcsEndpoint() {
	return acsEndpoint;
}

public void setAcsEndpoint(String acsEndpoint) {
	this.acsEndpoint = acsEndpoint;
}

public AuthenticationMethod getAuthenticationMethod() {
	return authenticationMethod;
}

public void setAuthenticationMethod(AuthenticationMethod authenticationMethod) {
	this.authenticationMethod = authenticationMethod;
}

public AuthenticationMethod getDefaultAuthenticationMethod() {
	return defaultAuthenticationMethod;
}

public void setDefaultAuthenticationMethod(AuthenticationMethod defaultAuthenticationMethod) {
	this.defaultAuthenticationMethod = defaultAuthenticationMethod;
}

public String getIdpPrivateKey() {
	return idpPrivateKey;
}

public String getIdpCertificate() {
	return idpCertificate;
}

}
