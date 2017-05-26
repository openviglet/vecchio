package com.viglet.vecchio.rest;

import java.util.regex.Pattern;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import javax.servlet.ServletException;

import com.viglet.vecchio.proxy.VigProxy;

public class VigRestRequest {

	// Accommodate two requests, one for all resources, another for a specific
	// resource
	private Pattern regExAllPattern = Pattern.compile("/turing/entity");
	private Pattern regExIdPattern = Pattern.compile("/resource/([0-9]*)");

	private Integer id;

	public VigRestRequest(String pathInfo, OutputStream ops) throws ServletException {

		try {
			VigRestMaps vigRestMaps = new VigRestMaps();
			for (VigRestMap vigRestMap : vigRestMaps.getVigRestMaps()) {
				if (vigRestMap.getPattern().matcher(pathInfo).matches()) {

					VigProxy vigProxy = new VigProxy(vigRestMap.getUrl(), ops);
					return;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// regex parse pathInfo
		Matcher matcher;

		// Check for ID case first, since the All pattern would also match
		matcher = regExIdPattern.matcher(pathInfo);
		if (matcher.find()) {
			id = Integer.parseInt(matcher.group(1));
			return;
		}

		throw new ServletException("Invalid URI");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}