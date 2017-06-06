package com.viglet.vecchio.rest;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class VigRestIndex {

	public static void index(HttpServletResponse response) throws IOException {
		response.sendRedirect("/console");
		
	}
}
