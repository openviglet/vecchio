package com.viglet.vecchio.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the VigOAuthAuthorizationCodes database table.
 * 
 */
@Entity
@Table(name="VecOAuthAuthorizationCodes")
@NamedQuery(name="VecOAuthAuthorizationCode.findAll", query="SELECT v FROM VecOAuthAuthorizationCode v")
public class VecOAuthAuthorizationCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private VecOAuthAuthorizationCodePK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date expires;

	@Column(name="id_token", nullable=false, length=255)
	private String idToken;

	@Column(name="redirect_uri", nullable=false, length=200)
	private String redirectUri;

	@Column(nullable=true, length=50)
	private String scope;

	@Column(name="user_id", nullable=true, length=50)
	private String userId;

	public VecOAuthAuthorizationCode() {
	}

	public VecOAuthAuthorizationCodePK getId() {
		return this.id;
	}

	public void setId(VecOAuthAuthorizationCodePK id) {
		this.id = id;
	}

	public Date getExpires() {
		return this.expires;
	}

	public void setExpires(Date expires) {
		this.expires = expires;
	}

	public String getIdToken() {
		return this.idToken;
	}

	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}

	public String getRedirectUri() {
		return this.redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}