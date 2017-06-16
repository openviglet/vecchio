package com.viglet.vecchio.persistence.service;

import java.util.List;

import javax.persistence.TypedQuery;

import com.viglet.vecchio.persistence.model.VecApp;

public class VecAppService   extends VecBaseService{

	public void save(VecApp vecApp) {
		em.getTransaction().begin();
		em.persist(vecApp);
		em.getTransaction().commit();
	}

	public List<VecApp> listAll() {
		TypedQuery<VecApp> q = em.createNamedQuery("VecApp.findAll", VecApp.class);
		return q.getResultList();
	}

	public VecApp getApp(int appId) {
		return em.find(VecApp.class, appId);
	}

	public VecApp getAppByAccessToken(String accessToken) {
		TypedQuery<VecApp> q = em.createQuery("SELECT a FROM VecApp a where a.accessToken = :accessToken ", VecApp.class).setParameter("accessToken", accessToken);
		return q.getSingleResult();
	}
	public boolean deleteApp(int appId) {
		VecApp vecApp = em.find(VecApp.class, appId);
		em.getTransaction().begin();
		em.remove(vecApp);
		em.getTransaction().commit();
		return true;
	}
}
