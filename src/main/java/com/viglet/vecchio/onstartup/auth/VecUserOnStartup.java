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

package com.viglet.vecchio.onstartup.auth;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.viglet.vecchio.persistence.model.auth.VecGroup;
import com.viglet.vecchio.persistence.model.auth.VecUser;
import com.viglet.vecchio.persistence.repository.auth.VecGroupRepository;
import com.viglet.vecchio.persistence.repository.auth.VecUserRepository;

@Component
public class VecUserOnStartup {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private VecUserRepository vecUserRepository;
	@Autowired
	private VecGroupRepository vecGroupRepository;
	
	public void createDefaultRows() {

		if (vecUserRepository.findAll().isEmpty()) {
			
			VecGroup vecGroup = vecGroupRepository.findByName("Administrator");
			
			Set<VecGroup> vecGroups = new HashSet<>();
			vecGroups.add(vecGroup);
					

			VecUser vecUser = new VecUser();

			vecUser.setEmail("admin@localhost.local");
			vecUser.setFirstName("Admin");
			vecUser.setLastLogin(new Date());
			vecUser.setLastName("Administrator");
			vecUser.setLoginTimes(0);
			vecUser.setPassword(passwordEncoder.encode("admin"));
			vecUser.setRealm("default");
			vecUser.setUsername("admin");
			vecUser.setEnabled(1);

			vecUser.setVecGroups(vecGroups);
			
			vecUserRepository.save(vecUser);			
						
			
			vecUser = new VecUser();

			vecUser.setEmail("sample@localhost.local");
			vecUser.setFirstName("Sample user");
			vecUser.setLastLogin(new Date());
			vecUser.setLastName("Sample");
			vecUser.setLoginTimes(0);
			vecUser.setPassword(passwordEncoder.encode("sample123"));
			vecUser.setRealm("default");
			vecUser.setUsername("sample");
			vecUser.setEnabled(1);
			
			vecUserRepository.save(vecUser);			
		}

	}
}
