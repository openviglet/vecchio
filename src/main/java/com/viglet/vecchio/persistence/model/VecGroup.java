package com.viglet.vecchio.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the ShUser database table.
 * 
 */
@Entity
@NamedQuery(name = "VecGroup.findAll", query = "SELECT g FROM VecGroup g")
public class VecGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private String description;

	// bi-directional many-to-one association to VigEntity
	@ManyToOne
	@JoinColumn(name = "role_id")
	private VecRole vecRole;

	public VecGroup() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
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