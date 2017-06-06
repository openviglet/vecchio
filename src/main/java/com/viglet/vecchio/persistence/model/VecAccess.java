package com.viglet.vecchio.persistence.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the vigTerm database table.
 * 
 */
@Entity
@Table(name = "vecAccess")
@NamedQuery(name = "VecAccess.findAll", query = "SELECT v FROM VecAccess v")
public class VecAccess implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;
	@Column(name = "date_request", nullable = false)
	private Date dateRequest;

	@Column(name = "request", nullable = false, length = 255)
	private String request;

	@Column(name = "response_time", nullable = false)
	private float responseTime;

	// bi-directional many-to-one association to VigEntity
	@ManyToOne
	@JoinColumn(name = "mapping_id")
	private VecMapping vecMapping;

	public VecAccess() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateRequest() {
		return dateRequest;
	}

	public void setDateRequest(Date dateRequest) {
		this.dateRequest = dateRequest;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public float getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(float responseTime) {
		this.responseTime = responseTime;
	}

	public VecMapping getVecMapping() {
		return vecMapping;
	}

	public void setVecMapping(VecMapping vecMapping) {
		this.vecMapping = vecMapping;
	}

}