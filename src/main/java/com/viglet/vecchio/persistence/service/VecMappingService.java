package com.viglet.vecchio.persistence.service;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.viglet.vecchio.persistence.model.VecApp;
import com.viglet.vecchio.persistence.model.VecMapping;

public class VecMappingService extends VecBaseService {

	public void save(VecMapping vecMapping) {
		em.getTransaction().begin();
		em.persist(vecMapping);
		em.getTransaction().commit();
	}

	public List<VecMapping> listAll() {
		try {
			TypedQuery<VecMapping> q = em.createNamedQuery("VecMapping.findAll", VecMapping.class);
			return q.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public boolean contextExists(String context) {
		try {
			TypedQuery<VecMapping> q = em
					.createQuery("SELECT m FROM VecMapping m where m.pattern = :context ", VecMapping.class)
					.setParameter("context", context);
			q.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
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
