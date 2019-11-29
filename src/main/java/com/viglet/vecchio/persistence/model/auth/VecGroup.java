package com.viglet.vecchio.persistence.model.auth;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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

	@ManyToMany(mappedBy = "vecGroups")
	private Set<VecRole> vecRoles = new HashSet<>();

	@ManyToMany(mappedBy = "vecGroups")
	private Set<VecUser> vecUsers = new HashSet<>();

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

	public Set<VecUser> getVecUsers() {
		return this.vecUsers;
	}

	public void setVecUsers(Set<VecUser> vecUsers) {
		this.vecUsers.clear();
		if (vecUsers != null) {
			this.vecUsers.addAll(vecUsers);
		}
	}
}