package com.viglet.vecchio.rest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class VigRestMaps {
	List<VigRestMap> vigRestMaps = new ArrayList<VigRestMap>();

	public VigRestMaps() {
		try {
			vigRestMaps.add(
					new VigRestMap(Pattern.compile("/turing/entity"), new URL("https://api.viglet.ai/turing/entity")));
			vigRestMaps.add(new VigRestMap(Pattern.compile("/github/openviglet"),
					new URL("https://api.github.com/users/openviglet")));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<VigRestMap> getVigRestMaps() {
		return vigRestMaps;
	}

	public void setVigRestMaps(List<VigRestMap> vigRestMaps) {
		this.vigRestMaps = vigRestMaps;
	}
	
}
