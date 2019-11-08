package com.viglet.vecchio.persistence.model.oauth;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the VigOAuthScopes database table.
 * 
 */
@Entity
@Table(name="VecOAuthScopes")
@NamedQuery(name="VecOAuthScope.findAll", query="SELECT v FROM VecOAuthScope v")
public class VecOAuthScope implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(nullable=false, length=50)
	private String scope;

	@Column(name="is_default", nullable=false)
	private byte isDefault;

	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public byte getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(byte isDefault) {
		this.isDefault = isDefault;
	}

}