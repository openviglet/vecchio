package com.viglet.vecchio.persistence.model.oauth;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the VecOAuthRefreshTokens database table.
 * 
 */
@Entity
@Table(name="VecOAuthRefreshTokens")
@NamedQuery(name="VecOAuthRefreshToken.findAll", query="SELECT v FROM VecOAuthRefreshToken v")
public class VecOAuthRefreshToken implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private VecOAuthRefreshTokenPK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date expires;

	@Column(nullable=false, length=50)
	private String scope;

	@Column(name="user_id", nullable=false, length=50)
	private String userId;

	public VecOAuthRefreshTokenPK getId() {
		return this.id;
	}

	public void setId(VecOAuthRefreshTokenPK id) {
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

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}