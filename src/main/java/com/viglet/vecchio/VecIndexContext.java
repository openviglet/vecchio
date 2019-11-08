/*
 * Copyright (C) 2016-2019 the original author or authors. 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.viglet.vecchio;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.viglet.vecchio.rest.VigRestRequest;

@Controller
public class VecIndexContext {
	@Autowired
	VigRestRequest vigRestRequest;
	
	@RequestMapping("/")
	private void index(HttpServletRequest request, HttpServletResponse response, final Principal principal)
			throws IOException {
		response.sendRedirect("/console");

	}

	@RequestMapping("/**")
	private void indexAnyRequest(HttpServletRequest request, HttpServletResponse response, final Principal principal)
			throws IOException {
		try {
			vigRestRequest.run(request.getPathInfo(), response.getOutputStream(),
					request);
		} catch (ServletException | OAuthSystemException e) {
			response.setStatus(400);
			response.resetBuffer();
			e.printStackTrace();

		}

	}
}
