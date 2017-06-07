package com.viglet.vecchio.persistence.service;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.json.JSONArray;
import org.json.JSONObject;

import com.viglet.vecchio.persistence.model.VecAccess;
import com.viglet.vecchio.persistence.model.VecMapping;

public class VecAccessService {
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("vecchio-app");
	EntityManager em = factory.createEntityManager();

	public void save(VecAccess vecAccess) {
		em.getTransaction().begin();
		em.persist(vecAccess);
		em.getTransaction().commit();
	}

	public List<VecAccess> listAll() {
		TypedQuery<VecAccess> q = em.createNamedQuery("VecAccess.findAll", VecAccess.class);
		return q.getResultList();
	}

	public JSONArray responseTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		List<VecAccess> listAll = this.listAll();
		JSONArray requests = new JSONArray();
		JSONArray values = new JSONArray();
		int x = 0;
		HashMap<String, JSONArray> hm = new HashMap<String, JSONArray>();
		for (VecAccess vecAccess : listAll) {
			x++;
			JSONObject value = new JSONObject();
			String date = sdf.format(vecAccess.getDateRequest());
			value.put("x", x);
			value.put("y", vecAccess.getResponseTime() / 1000);
			if (hm.containsKey(vecAccess.getRequest())) {
				JSONArray valuesHM = hm.get(vecAccess.getRequest());
				valuesHM.put(value);
			} else {
				hm.put(vecAccess.getRequest(), new JSONArray().put(value));
			}

		}
		for (String key : hm.keySet()) {
			Random rand = new Random();
			int r = rand.nextInt(255);
			int g = rand.nextInt(255);
			int b = rand.nextInt(255);
			JSONObject request = new JSONObject();
			request.put("values", hm.get(key));
			request.put("key", key);
			request.put("color", "rgb(" + r + "," + g + ","  + b + ")");
			request.put("area", false);
			requests.put(request);
		}
		
		
		return requests;
	}
}
