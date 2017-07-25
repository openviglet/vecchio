package com.viglet.vecchio.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the VigOAuthAccessTokens database table.
 * 
 */
@Entity
@Table(name="VecOAuthAccessTokens")
@NamedQuery(name="VecOAuthAccessToken.findAll", query="SELECT v FROM VecOAuthAccessToken v")
public class VecOAuthAccessToken implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private VecOAuthAccessTokenPK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date expires;

	@Column(length=50)
	private String scope;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private VecUser vecUser;

	public VecOAuthAccessToken() {
	}

	public VecOAuthAccessTokenPK getId() {
		return this.id;
	}

	public void setId(VecOAuthAccessTokenPK id) {
		this.id = id;
	}

	public Date getExpires() {
		return this.expires;
	}

	public void setExpires(Date expires) {
		this.expires = expires;
	}

	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public VecUser getVecUser() {
		return this.vecUser;
	}

	public void setVecUser(VecUser vecUser) {
		this.vecUser = vecUser;
	}

}