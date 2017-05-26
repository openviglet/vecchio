package com.viglet.vecchio.rest;

import java.net.URL;
import java.util.regex.Pattern;

public class VigRestMap {

	Pattern pattern;
	URL url;
	
	public VigRestMap(Pattern pattern, URL url) {
		this.setPattern(pattern);
		this.setUrl(url);
	}
	public Pattern getPattern() {
		return pattern;
	}
	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}
	public URL getUrl() {
		return url;
	}
	public void setUrl(URL url) {
		this.url = url;
	}
	
}
