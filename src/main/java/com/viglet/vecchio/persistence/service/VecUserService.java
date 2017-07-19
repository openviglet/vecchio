package com.viglet.vecchio.persistence.service;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.viglet.vecchio.persistence.model.VecUser;

public class VecUserService extends VecBaseService {

	public void save(VecUser vecUser) {
		em.getTransaction().begin();
		em.persist(vecUser);
		em.getTransaction().commit();
	}

	public List<VecUser> listAll() {
		TypedQuery<VecUser> q = em.createNamedQuery("VecUser.findAll", VecUser.class);
		return q.getResultList();
	}

	public VecUser getUser(int userId) {
		return em.find(VecUser.class, userId);
	}

	public VecUser validatePassword(String username, String password) {
		try {
			TypedQuery<VecUser> q = em
					.createQuery("SELECT u FROM VecUser u WHERE u.username = :username AND u.password = :password",
							VecUser.class)
					.setParameter("username", username).setParameter("password", password);
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public boolean deleteUser(int userId) {
		VecUser vecUser = em.find(VecUser.class, userId);
		em.getTransaction().begin();
		em.remove(vecUser);
		em.getTransaction().commit();
		return true;
	}
}
