package com.viglet.vecchio.persistence.service;

import java.util.List;

import javax.persistence.TypedQuery;

import com.viglet.vecchio.persistence.model.VecRole;

public class VecRoleService   extends VecBaseService{

	public void save(VecRole vecRole) {
		em.getTransaction().begin();
		em.persist(vecRole);
		em.getTransaction().commit();
	}

	public List<VecRole> listAll() {
		TypedQuery<VecRole> q = em.createNamedQuery("VecRole.findAll", VecRole.class);
		return q.getResultList();
	}

	public VecRole getRole(int userId) {
		return em.find(VecRole.class, userId);
	}

	public boolean deleteRole(int userId) {
		VecRole vecRole = em.find(VecRole.class, userId);
		em.getTransaction().begin();
		em.remove(vecRole);
		em.getTransaction().commit();
		return true;
	}
}
