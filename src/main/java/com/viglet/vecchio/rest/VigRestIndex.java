package com.viglet.vecchio.rest;

import java.io.PrintWriter;

public class VigRestIndex {

	public static void index(PrintWriter out) {
		out.println("<h1>Viglet Vecchio</h1><br>");
		VigRestMaps vigRestMaps = new VigRestMaps();
		for (VigRestMap vigRestMap : vigRestMaps.getVigRestMaps()) {
			String strPattern = vigRestMap.getPattern().pattern();
			String strURL = vigRestMap.getUrl().toString();
			
			out.println(String.format(
					"<a href='%1$s' target='_blank'>%1$s</a> that proxies to <a href='%2$s' target='_blank'>%2$s</a> <br>",
					strPattern, strURL));

		}

	}
}
