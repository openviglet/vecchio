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
import java.util.Set;

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

import com.viglet.vecchio.persistence.model.auth.VecGroup;
import com.viglet.vecchio.persistence.model.auth.VecUser;
import com.viglet.vecchio.persistence.repository.auth.VecGroupRepository;
import com.viglet.vecchio.persistence.repository.auth.VecUserRepository;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/v2/group")
@Api(tags = "Group", description = "Group API")
public class VecGroupAPI {

	@Autowired
	private VecGroupRepository vecGroupRepository;
	@Autowired
	private VecUserRepository vecUserRepository;

	@GetMapping
	public List<VecGroup> vecGroupList() {
		return vecGroupRepository.findAll();
	}

	@GetMapping("/{id}")
	public VecGroup vecGroupEdit(@PathVariable String id) {
		VecGroup vecGroup = vecGroupRepository.findById(id).get();
		List<VecGroup> vecGroups = new ArrayList<>();
		vecGroups.add(vecGroup);
		vecGroup.setVecUsers(vecUserRepository.findByVecGroupsIn(vecGroups));
		return vecGroup;
	}

	@PutMapping("/{id}")
	public VecGroup vecGroupUpdate(@PathVariable String id, @RequestBody VecGroup vecGroup) {
		vecGroupRepository.save(vecGroup);
		VecGroup vecGroupRepos = vecGroupRepository.findById(vecGroup.getId()).get();
		List<VecGroup> vecGroups = new ArrayList<>();
		vecGroups.add(vecGroup);
		Set<VecUser> vecUsers = vecUserRepository.findByVecGroupsIn(vecGroups);
		for (VecUser vecUser : vecUsers) {
			vecUser.getVecGroups().remove(vecGroupRepos);
			vecUserRepository.saveAndFlush(vecUser);
		}
		for (VecUser vecUser : vecGroup.getVecUsers()) {
			VecUser vecUserRepos = vecUserRepository.findByUsername(vecUser.getUsername());
			vecUserRepos.getVecGroups().add(vecGroup);
			vecUserRepository.saveAndFlush(vecUserRepos);
		}

		return vecGroup;
	}

	@Transactional
	@DeleteMapping("/{id}")
	public boolean vecGroupDelete(@PathVariable String id) {
		vecGroupRepository.delete(id);
		return true;
	}

	@PostMapping
	public VecGroup vecGroupAdd(@RequestBody VecGroup vecGroup) {

		vecGroupRepository.save(vecGroup);

		return vecGroup;
	}

	@GetMapping("/model")
	public VecGroup vecGroupStructure() {
		return new VecGroup();

	}

}
