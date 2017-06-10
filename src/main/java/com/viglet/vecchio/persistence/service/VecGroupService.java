package com.viglet.vecchio.persistence.service;

import java.util.List;

import javax.persistence.TypedQuery;

import com.viglet.vecchio.persistence.model.VecGroup;

public class VecGroupService   extends VecBaseService{

	public void save(VecGroup vecGroup) {
		em.getTransaction().begin();
		em.persist(vecGroup);
		em.getTransaction().commit();
	}

	public List<VecGroup> listAll() {
		TypedQuery<VecGroup> q = em.createNamedQuery("VecGroup.findAll", VecGroup.class);
		return q.getResultList();
	}

	public VecGroup getGroup(int groupId) {
		return em.find(VecGroup.class, groupId);
	}

	public boolean deleteGroup(int groupId) {
		VecGroup vecGroup = em.find(VecGroup.class, groupId);
		em.getTransaction().begin();
		em.remove(vecGroup);
		em.getTransaction().commit();
		return true;
	}
}
