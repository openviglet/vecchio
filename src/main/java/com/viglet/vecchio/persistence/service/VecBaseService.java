package com.viglet.vecchio.persistence.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class VecBaseService {
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("vecchio-app");
	EntityManager em = factory.createEntityManager();

}
