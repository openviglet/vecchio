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

package com.viglet.vecchio.persistence.repository.auth;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.viglet.vecchio.persistence.model.auth.VecGroup;
import com.viglet.vecchio.persistence.model.auth.VecUser;

public interface VecGroupRepository extends JpaRepository<VecGroup, String> {

	List<VecGroup> findAll();

	@SuppressWarnings("unchecked")
	VecGroup save(VecGroup vecGroup);

	VecGroup findByName(String name);
	
	Set<VecGroup> findByVecUsersIn(Collection<VecUser> users);
	
	int countByNameAndVecUsersIn(String name, Collection<VecUser> vecUsers);
	
	@Modifying
	@Query("delete from VecGroup g where g.id = ?1")
	void delete(String id);
}
