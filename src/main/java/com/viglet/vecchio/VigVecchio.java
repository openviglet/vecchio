package com.viglet.vecchio;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.viglet.vecchio.rest.VigRestIndex;
import com.viglet.vecchio.rest.VigRestRequest;

public class VigVecchio extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			if (request.getPathInfo().equals("/")) {
				VigRestIndex.index(response.getWriter());
			} else {
				try {
					VigRestRequest vigRestRequest = new VigRestRequest(request.getPathInfo(),
							response.getOutputStream());
				} catch (ServletException e) {
					response.setStatus(400);
					response.resetBuffer();
					e.printStackTrace();
				}
			}

	}

	public void destroy() {
		// do nothing.
	}
}
