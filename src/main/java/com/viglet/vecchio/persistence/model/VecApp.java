package com.viglet.vecchio.persistence.model;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonFilter;

/**
 * The persistent class for the ShUser database table.
 * 
 */
@Entity
@NamedQuery(name = "VecApp.findAll", query = "SELECT a FROM VecApp a")
@JsonFilter("myFilter")
public class VecApp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="name", nullable=false, length=50)
	private String name;

	@Column(name="description", nullable=true, length=255)
	private String description;
	
	@Column(name="api_key", nullable=true, length=50)
	private String apiKey;
	
	@Column(name="api_secret", nullable=true, length=50)
	private String apiSecret;
	
	@Column(name="access_token", nullable=true, length=50)
	private String accessToken;
	
	@Column(name="access_token_secret", nullable=true, length=50)
	private String accessTokenSecret;
	
	@Column(name="callback_url", nullable=true, length=255)
	private String callbackURL;

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

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiSecret() {
		return apiSecret;
	}

	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

	public String getCallbackURL() {
		return callbackURL;
	}

	public void setCallbackURL(String callbackURL) {
		this.callbackURL = callbackURL;
	}
	

}