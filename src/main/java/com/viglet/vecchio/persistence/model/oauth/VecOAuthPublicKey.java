package com.viglet.vecchio.persistence.model.oauth;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the VecOAuthPublicKeys database table.
 * 
 */
@Entity
@Table(name="VecOAuthPublicKeys")
@NamedQuery(name="VecOAuthPublicKey.findAll", query="SELECT v FROM VecOAuthPublicKey v")
public class VecOAuthPublicKey implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="client_id", nullable=false, length=50)
	private String clientId;

	@Column(name="encryption_algorithm", nullable=false, length=80)
	private String encryptionAlgorithm;

	@Column(name="private_key", nullable=false, length=8000)
	private String privateKey;

	@Column(name="public_key", nullable=false, length=8000)
	private String publicKey;

	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getEncryptionAlgorithm() {
		return this.encryptionAlgorithm;
	}

	public void setEncryptionAlgorithm(String encryptionAlgorithm) {
		this.encryptionAlgorithm = encryptionAlgorithm;
	}

	public String getPrivateKey() {
		return this.privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKey() {
		return this.publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

}