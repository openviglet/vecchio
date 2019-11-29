/*
 * Copyright (C) 2016-2019 Alexandre Oliveira <alexandre.oliveira@viglet.com> 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as publiveced by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You vecould have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.viglet.vecchio.api.auth;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viglet.vecchio.bean.VecCurrentUser;
import com.viglet.vecchio.persistence.model.auth.VecGroup;
import com.viglet.vecchio.persistence.model.auth.VecUser;
import com.viglet.vecchio.persistence.repository.auth.VecGroupRepository;
import com.viglet.vecchio.persistence.repository.auth.VecUserRepository;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/v2/user")
@Api(tags = "User", description = "User API")
public class VecUserAPI {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private VecUserRepository vecUserRepository;
	@Autowired
	private VecGroupRepository vecGroupRepository;

	@GetMapping
	public List<VecUser> vecUserList() {
		return vecUserRepository.findAll();
	}

	@GetMapping("/current")
	public VecCurrentUser vecUserCurrent() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			boolean isAdmin = false;
			String currentUserName = authentication.getName();
			VecUser vecUser = vecUserRepository.findByUsername(currentUserName);
			vecUser.setPassword(null);
			if (vecUser.getVecGroups() != null) {
				for (VecGroup vecGroup : vecUser.getVecGroups()) {
					if (vecGroup.getName().equals("Administrator"))
						isAdmin = true;
				}
			}
			VecCurrentUser vecCurrentUser = new VecCurrentUser();
			vecCurrentUser.setUsername(vecUser.getUsername());
			vecCurrentUser.setFirstName(vecUser.getFirstName());
			vecCurrentUser.setLastName(vecUser.getLastName());
			vecCurrentUser.setAdmin(isAdmin);

			return vecCurrentUser;
		}

		return null;
	}

	@GetMapping("/{username}")
	public VecUser vecUserEdit(@PathVariable String username) {
		VecUser vecUser = vecUserRepository.findByUsername(username);
		if (vecUser != null) {
			vecUser.setPassword(null);
			List<VecUser> vecUsers = new ArrayList<>();
			vecUsers.add(vecUser);
			vecUser.setVecGroups(vecGroupRepository.findByVecUsersIn(vecUsers));
		}
		return vecUser;
	}

	@PutMapping("/{username}")
	public VecUser vecUserUpdate(@PathVariable String username, @RequestBody VecUser vecUser) {
		VecUser vecUserEdit = vecUserRepository.findByUsername(username);
		if (vecUserEdit != null) {
			vecUserEdit.setFirstName(vecUser.getFirstName());
			vecUserEdit.setLastName(vecUser.getLastName());
			vecUserEdit.setEmail(vecUser.getEmail());
			if (vecUser.getPassword() != null && vecUser.getPassword().trim().length() > 0) {
				vecUserEdit.setPassword(passwordEncoder.encode(vecUser.getPassword()));
			}
			vecUserEdit.setVecGroups(vecUser.getVecGroups());
			vecUserRepository.save(vecUserEdit);
		}
		return vecUserEdit;
	}

	@Transactional
	@DeleteMapping("/{username}")
	public boolean vecUserDelete(@PathVariable String username) {
		vecUserRepository.delete(username);
		return true;
	}

	@PostMapping("/{username}")
	public VecUser vecUserAdd(@PathVariable String username, @RequestBody VecUser vecUser) {
		if (vecUser.getPassword() != null && vecUser.getPassword().trim().length() > 0) {
			vecUser.setPassword(passwordEncoder.encode(vecUser.getPassword()));
		}

		vecUserRepository.save(vecUser);

		return vecUser;
	}

	@GetMapping("/model")
	public VecUser vecUserStructure() {
		return new VecUser();

	}

}
