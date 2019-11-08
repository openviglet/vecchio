package com.viglet.vecchio.persistence.model.oauth;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the VecOAuthClient database table.
 * 
 */
@Entity
@Table(name="VecOAuthClients")
@NamedQuery(name="VecOAuthClient.findAll", query="SELECT v FROM VecOAuthClient v")
public class VecOAuthClient implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="client_id", nullable=false, length=50)
	private String clientId;

	@Column(name="client_secret", nullable=false, length=20)
	private String clientSecret;

	@Column(name="redirect_uri", nullable=false, length=255)
	private String redirectUri;

	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return this.clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getRedirectUri() {
		return this.redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

}