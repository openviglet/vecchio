package com.viglet.vecchio.api.monitoring;

import java.util.HashMap;
import java.util.List;

public class VecResponseTime {

	private List<HashMap<String, Float>> values;

	private String key;

	private String color;

	private boolean area;

	public List<HashMap<String, Float>> getValues() {
		return values;
	}

	public void setValues(List<HashMap<String, Float>> values) {
		this.values = values;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isArea() {
		return area;
	}

	public void setArea(boolean area) {
		this.area = area;
	}

}
