package com.viglet.vecchio.persistence.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.viglet.vecchio.persistence.model.VecMapping;

public class VecMappingService {
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("vecchio-app");
	EntityManager em = factory.createEntityManager();

	public void save(VecMapping vecMapping) {
		em.getTransaction().begin();
		em.persist(vecMapping);
		em.getTransaction().commit();
	}

	public List<VecMapping> listAll() {
		TypedQuery<VecMapping> q = em.createNamedQuery("VecMapping.findAll", VecMapping.class);
		return q.getResultList();
	}

	public VecMapping getMapping(int mappingId) {
		return em.find(VecMapping.class, mappingId);
	}

	public boolean deleteMapping(int mappingId) {
		VecMapping vecMapping = em.find(VecMapping.class, mappingId);
		em.getTransaction().begin();
		em.remove(vecMapping);
		em.getTransaction().commit();
		return true;
	}

}