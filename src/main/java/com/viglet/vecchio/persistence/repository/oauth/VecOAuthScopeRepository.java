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

package com.viglet.vecchio.persistence.repository.oauth;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.viglet.vecchio.persistence.model.oauth.VecOAuthScope;

@Repository
public interface VecOAuthScopeRepository extends JpaRepository<VecOAuthScope, String> {

	List<VecOAuthScope> findAll();

	Optional<VecOAuthScope> findById(String id);

	@SuppressWarnings("unchecked")
	VecOAuthScope save(VecOAuthScope vecOAuthScope);
	
	@Modifying
	@Query("delete from VecOAuthScope os where os.id = ?1")
	void delete(String id);
}
