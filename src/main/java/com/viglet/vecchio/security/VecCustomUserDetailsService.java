/*
 * Copyright (C) 2016-2019 Alexandre Oliveira <alexandre.oliveira@viglet.com> 
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

package com.viglet.vecchio.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.viglet.vecchio.persistence.model.auth.VecGroup;
import com.viglet.vecchio.persistence.model.auth.VecRole;
import com.viglet.vecchio.persistence.model.auth.VecUser;
import com.viglet.vecchio.persistence.repository.auth.VecGroupRepository;
import com.viglet.vecchio.persistence.repository.auth.VecRoleRepository;
import com.viglet.vecchio.persistence.repository.auth.VecUserRepository;

@Service("customUserDetailsService")
public class VecCustomUserDetailsService implements UserDetailsService {
	@Autowired
	private VecUserRepository vecUserRepository;
	@Autowired
	private VecRoleRepository vecRoleRepository;
	@Autowired
	private VecGroupRepository vecGroupRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		VecUser vecUser = vecUserRepository.findByUsername(username);
		if (null == vecUser) {
			throw new UsernameNotFoundException("No user present with username: " + username);
		} else {
			List<VecUser> users = new ArrayList<>();
			users.add(vecUser);
			Set<VecGroup> shGroups = vecGroupRepository.findByVecUsersIn(users);
			Set<VecRole> shRoles = vecRoleRepository.findByVecGroupsIn(shGroups);

			List<String> roles = new ArrayList<>();
			for (VecRole shRole : shRoles) {
				roles.add(shRole.getName());
			}
			return new VecCustomUserDetails(vecUser, roles);
		}
	}

}
