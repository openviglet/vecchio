package com.viglet.vecchio.persistence.service;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.viglet.vecchio.persistence.model.oauth.VecOAuthAccessToken;
import com.viglet.vecchio.persistence.model.oauth.VecOAuthAccessTokenPK;

public class VecOAuthAccessTokenService extends VecBaseService {

	public void save(VecOAuthAccessToken vecOAuthAccessToken) {
		if (vecOAuthAccessToken.getVecUser() != null) {
			vecOAuthAccessToken.setVecUser(em.merge(vecOAuthAccessToken.getVecUser()));
		}
		em.getTransaction().begin();
		em.persist(vecOAuthAccessToken);
		em.getTransaction().commit();
	}

	public List<VecOAuthAccessToken> listAll() {
		TypedQuery<VecOAuthAccessToken> q = em.createNamedQuery("VecOAuthAccessToken.findAll",
				VecOAuthAccessToken.class);
		return q.getResultList();
	}

	public VecOAuthAccessToken getAccessToken(String accessToken) {
		try {
			TypedQuery<VecOAuthAccessToken> q = em
					.createQuery("SELECT a FROM VecOAuthAccessToken a where a.id.accessToken = :accessToken ",
							VecOAuthAccessToken.class)
					.setParameter("accessToken", accessToken);
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public VecOAuthAccessToken getAccessTokenByClientId(String clientId) {
		try {
			TypedQuery<VecOAuthAccessToken> q = em
					.createQuery("SELECT a FROM VecOAuthAccessToken a where a.id.clientId = :clientId ",
							VecOAuthAccessToken.class)
					.setParameter("clientId", clientId);
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public boolean deletetAccessToken(VecOAuthAccessTokenPK id) {
		VecOAuthAccessToken vecOAuthAccessToken = em.find(VecOAuthAccessToken.class, id);
		em.getTransaction().begin();
		em.remove(vecOAuthAccessToken);
		em.getTransaction().commit();
		return true;
	}
}
