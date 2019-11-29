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

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viglet.vecchio.persistence.model.auth.VecRole;
import com.viglet.vecchio.persistence.repository.auth.VecRoleRepository;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/v2/role")
@Api(tags = "Role", description = "Role API")
public class VecRoleAPI {

	@Autowired
	private VecRoleRepository vecRoleRepository;

	@GetMapping
	public List<VecRole> vecRoleList() {
		return vecRoleRepository.findAll();
	}

	@GetMapping("/{id}")
	public VecRole vecRoleEdit(@PathVariable String id) {
		return vecRoleRepository.findById(id).get();
	}

	@PutMapping("/{id}")
	public VecRole vecRoleUpdate(@PathVariable String id, @RequestBody VecRole vecRole) {
		vecRoleRepository.save(vecRole);
		return vecRole;
	}

	@Transactional
	@DeleteMapping("/{id}")
	public boolean vecRoleDelete(@PathVariable String id) {
		vecRoleRepository.delete(id);
		return true;
	}

	@PostMapping
	public VecRole vecRoleAdd(@RequestBody VecRole vecRole) {

		vecRoleRepository.save(vecRole);

		return vecRole;
	}

	@GetMapping("/model")
	public VecRole vecRoleStructure() {
		return new VecRole();

	}

}
