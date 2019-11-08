package com.viglet.vecchio.persistence.model.auth;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the VecGroup database table.
 * 
 */
@Entity
@NamedQuery(name = "VecGroup.findAll", query = "SELECT g FROM VecGroup g")
public class VecGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "UUID", strategy = "com.viglet.vecchio.jpa.VecUUIDGenerator")
	@GeneratedValue(generator = "UUID")
	@Column(name = "id", updatable = false, nullable = false)
	private String id;

	private String name;

	private String description;

	// bi-directional many-to-one association to VigEntity
	@ManyToOne
	@JoinColumn(name = "role_id")
	private VecRole vecRole;

	public VecGroup() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public VecRole getVecRole() {
		return vecRole;
	}

	public void setVecRole(VecRole vecRole) {
		this.vecRole = vecRole;
	}
}