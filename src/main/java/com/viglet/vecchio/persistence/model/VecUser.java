package com.viglet.vecchio.persistence.model;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the ShUser database table.
 * 
 */
@Entity
@NamedQuery(name = "VecUser.findAll", query = "SELECT u FROM VecUser u")
@JsonIgnoreProperties({ "vecOAuthAccessTokens" })
public class VecUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String confirmEmail;

	private String email;

	private String firstName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;

	private String lastName;

	private int loginTimes;

	private String password;

	private String realm;

	private String recoverPassword;

	private String username;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private VecRole vecRole;

	@OneToMany(mappedBy = "vecUser")
	private List<VecOAuthAccessToken> vecOAuthAccessTokens;

	public VecUser() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getConfirmEmail() {
		return this.confirmEmail;
	}

	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Date getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getLoginTimes() {
		return this.loginTimes;
	}

	public void setLoginTimes(int loginTimes) {
		this.loginTimes = loginTimes;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealm() {
		return this.realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public String getRecoverPassword() {
		return this.recoverPassword;
	}

	public void setRecoverPassword(String recoverPassword) {
		this.recoverPassword = recoverPassword;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public VecRole getVecRole() {
		return vecRole;
	}

	public void setVecRole(VecRole vecRole) {
		this.vecRole = vecRole;
	}

	public List<VecOAuthAccessToken> getVecOAuthAccessTokens() {
		return vecOAuthAccessTokens;
	}

	public void setVecOAuthAccessTokens(List<VecOAuthAccessToken> vecOAuthAccessTokens) {
		this.vecOAuthAccessTokens = vecOAuthAccessTokens;
	}

	public VecOAuthAccessToken addVecOAuthAccessToken(VecOAuthAccessToken vecOAuthAccessToken) {
		getVecOAuthAccessTokens().add(vecOAuthAccessToken);
		vecOAuthAccessToken.setVecUser(this);
		return vecOAuthAccessToken;
	}

	public VecOAuthAccessToken removeVecUser(VecOAuthAccessToken vecOAuthAccessToken) {
		getVecOAuthAccessTokens().remove(vecOAuthAccessToken);
		vecOAuthAccessToken.setVecUser(null);

		return vecOAuthAccessToken;
	}
}