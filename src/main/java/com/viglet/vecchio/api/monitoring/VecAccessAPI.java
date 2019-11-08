package com.viglet.vecchio.api.monitoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viglet.vecchio.persistence.model.VecAccess;
import com.viglet.vecchio.persistence.repository.VecAccessRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/access")
@Api(value = "/access", tags = "Access", description = "Access")
public class VecAccessAPI {
	@Autowired
	private VecAccessRepository vecAccessRepository;

	@ApiOperation(value = "Show Access List")
	@GetMapping
	public List<VecAccess> list() {
		return vecAccessRepository.findAll();
	}

	@ApiOperation(value = "Create a Access")
	@PostMapping
	public VecAccess add(VecAccess vecAccess) {
		vecAccessRepository.save(vecAccess);
		return vecAccess;

	}

	@ApiOperation(value = "Show Response Time")
	@GetMapping("/response_time")
	public List<VecResponseTime> responseTime() {
		List<VecAccess> listAll = vecAccessRepository.findAll();
		List<VecResponseTime> requests = new ArrayList<>();
		int x = 0;
		HashMap<String, List<HashMap<String, Float>>> hm = new HashMap<>();
		for (VecAccess vecAccess : listAll) {
			x++;
			HashMap<String, Float> value = new HashMap<>();
			value.put("x", (float) x);
			value.put("y", vecAccess.getResponseTime() / 1000);
			if (hm.containsKey(vecAccess.getRequest())) {
				List<HashMap<String, Float>> valuesHM = hm.get(vecAccess.getRequest());
				valuesHM.add(value);
			} else {
				List<HashMap<String, Float>> floats = new ArrayList<>();
				floats.add(value);
				hm.put(vecAccess.getRequest(), floats);
			}

		}
		for (String key : hm.keySet()) {
			Random rand = new Random();
			int r = rand.nextInt(255);
			int g = rand.nextInt(255);
			int b = rand.nextInt(255);
			VecResponseTime vecResponseTime = new VecResponseTime();
			vecResponseTime.setValues(hm.get(key));
			vecResponseTime.setKey(key);
			vecResponseTime.setColor("rgb(" + r + "," + g + "," + b + ")");
			vecResponseTime.setArea(false);
			requests.add(vecResponseTime);
		}

		return requests;
	}
}