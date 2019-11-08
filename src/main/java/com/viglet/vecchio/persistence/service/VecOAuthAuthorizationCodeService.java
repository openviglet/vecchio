package com.viglet.vecchio.persistence.service;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.viglet.vecchio.persistence.model.oauth.VecOAuthAuthorizationCode;
import com.viglet.vecchio.persistence.model.oauth.VecOAuthAuthorizationCodePK;

public class VecOAuthAuthorizationCodeService extends VecBaseService {

	public void save(VecOAuthAuthorizationCode vecOAuthAuthorizationCode) {
		em.getTransaction().begin();
		em.persist(vecOAuthAuthorizationCode);
		em.getTransaction().commit();
	}

	public List<VecOAuthAuthorizationCode> listAll() {
		TypedQuery<VecOAuthAuthorizationCode> q = em.createNamedQuery("VecOAuthAuthorizationCode.findAll",
				VecOAuthAuthorizationCode.class);
		return q.getResultList();
	}

	public VecOAuthAuthorizationCode getAuthCodeByClientId(String clientId) {
		try {
			TypedQuery<VecOAuthAuthorizationCode> q = em
					.createQuery("SELECT a FROM VecOAuthAuthorizationCode a where a.id.clientId = :clientId ",
							VecOAuthAuthorizationCode.class)
					.setParameter("clientId", clientId);
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public boolean deletetAuthCode(VecOAuthAuthorizationCodePK id) {
		VecOAuthAuthorizationCode vecOAuthAuthorizationCode = em.find(VecOAuthAuthorizationCode.class, id);
		em.getTransaction().begin();
		em.remove(vecOAuthAuthorizationCode);
		em.getTransaction().commit();
		return true;
	}
}
