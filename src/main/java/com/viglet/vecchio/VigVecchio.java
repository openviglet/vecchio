package com.viglet.vecchio;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.viglet.vecchio.proxy.VigProxy;
import com.viglet.vecchio.rest.VigRestRequest;

public class VigVecchio extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//	PrintWriter out = response.getWriter();

//		out.println("GET request handling");
//		out.println(request.getPathInfo());
//		out.println(request.getParameterMap());
		try {
			VigRestRequest resourceValues = new VigRestRequest(request.getPathInfo(), response.getOutputStream());
	//		out.println(resourceValues.getId());
		} catch (ServletException e) {
			response.setStatus(400);
			response.resetBuffer();
			e.printStackTrace();
	//		out.println(e.toString());
		}
	//	out.close();

		// VigProxy vigProxy = new VigProxy(request, response);
	}

	public void destroy() {
		// do nothing.
	}
}
