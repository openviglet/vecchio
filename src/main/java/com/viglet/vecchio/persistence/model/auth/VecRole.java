package com.viglet.vecchio.persistence.model.auth;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

/**
 * The persistent class for the VecRole database table.
 * 
 */
@Entity
@Table(name = "vecRole")
@NamedQuery(name = "VecRole.findAll", query = "SELECT r FROM VecRole r")
public class VecRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "UUID", strategy = "com.viglet.vecchio.jpa.VecUUIDGenerator")
	@GeneratedValue(generator = "UUID")

	@Column(name = "id", updatable = false, nullable = false)
	private String id;


	@Column(name = "name", nullable = false, length = 50)
	private String name;

	@Column(nullable = true, length = 255)
	private String description;

	@ManyToMany
	private Set<VecGroup> vecGroups = new HashSet<>();

	public VecRole() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Set<VecGroup> getVecGroups() {
		return this.vecGroups;
	}

	public void setVecGroups(Set<VecGroup> vecGroups) {
		this.vecGroups.clear();
		if (vecGroups != null) {
			this.vecGroups.addAll(vecGroups);
		}
	}

}