package com.viglet.vecchio.persistence.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.viglet.vecchio.persistence.model.VecAccess;

public class VecAccessService {
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("vecchio-app");
	EntityManager em = factory.createEntityManager();
	
	public void save (VecAccess vecAccess) {
		em.getTransaction().begin();
		em.persist(vecAccess);
		em.getTransaction().commit();
	}
	public List<VecAccess> listAll () {
		TypedQuery<VecAccess> q = em.createNamedQuery("VecAccess.findAll", VecAccess.class);
		return q.getResultList();
	}
	
}
