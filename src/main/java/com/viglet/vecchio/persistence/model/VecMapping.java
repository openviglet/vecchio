package com.viglet.vecchio.persistence.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the vigTerm database table.
 * 
 */
@Entity
@Table(name = "vecMapping")
@NamedQuery(name = "VecMapping.findAll", query = "SELECT v FROM VecMapping v")
public class VecMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@Column(name = "pattern", nullable = false, length = 255)
	private String pattern;

	@Column(nullable = false, length = 255)
	private String url;
	
	// bi-directional many-to-one association to VecAccess
	@OneToMany(mappedBy="vecMapping")
	private List<VecAccess> vecAccesses;
	
	public VecMapping() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<VecAccess> getVecAccesses() {
		return vecAccesses;
	}

	public void setVecAccesses(List<VecAccess> vecAccesses) {
		this.vecAccesses = vecAccesses;
	}

	public VecAccess addVecAccess(VecAccess vecAccess) {
		getVecAccesses().add(vecAccess);
		vecAccess.setVecMapping(this);;

		return vecAccess;
	}

	public VecAccess removeVecAccess(VecAccess vecAccess) {
		getVecAccesses().remove(vecAccess);
		vecAccess.setVecMapping(null);

		return vecAccess;
	}

}