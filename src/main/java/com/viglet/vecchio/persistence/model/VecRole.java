package com.viglet.vecchio.persistence.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the vigTerm database table.
 * 
 */
@Entity
@Table(name = "vecRole")
@NamedQuery(name = "VecRole.findAll", query = "SELECT r FROM VecRole r")
public class VecRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@Column(name = "name", nullable = false, length = 50)
	private String name;

	@Column(nullable = true, length = 255)
	private String description;
	
	// bi-directional many-to-one association to VecAccess
	@OneToMany(mappedBy="vecRole")
	private List<VecUser> vecUsers;
	
	public VecRole() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public List<VecUser> getVecUsers() {
		return vecUsers;
	}

	public void setVecUsers(List<VecUser> vecUsers) {
		this.vecUsers = vecUsers;
	}

	public VecUser addVecUser(VecUser vecUser) {
		getVecUsers().add(vecUser);
		vecUser.setVecRole(this);;

		return vecUser;
	}

	public VecUser removeVecUser(VecUser vecUser) {
		getVecUsers().remove(vecUser);
		vecUser.setVecRole(null);

		return vecUser;
	}

}