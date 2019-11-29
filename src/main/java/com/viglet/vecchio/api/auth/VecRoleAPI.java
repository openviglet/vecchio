package com.viglet.vecchio.api.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viglet.vecchio.persistence.model.auth.VecRole;
import com.viglet.vecchio.persistence.repository.auth.VecRoleRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v2/role")
@Api(value = "/role", tags = "Role", description = "Role")
public class VecRoleAPI {
	@Autowired
	private VecRoleRepository vecRoleRepository;

	@ApiOperation(value = "Show Role List")
	@GetMapping
	public List<VecRole> list() {
		return vecRoleRepository.findAll();
	}

	@ApiOperation(value = "Show a Role")
	@GetMapping("/{id}")
	public VecRole edit(@PathVariable String id) {
		return vecRoleRepository.findById(id).get();
	}

	@ApiOperation(value = "Update a Role")
	@PutMapping("/{id}")
	public VecRole update(@PathVariable String id, VecRole vecRole) {
		VecRole vecRoleEdit = vecRoleRepository.findById(id).get();
		vecRoleEdit.setName(vecRole.getName());
		vecRoleEdit.setDescription(vecRole.getDescription());
		vecRoleRepository.save(vecRoleEdit);
		return vecRoleEdit;
	}

	@ApiOperation(value = "Delete a Role")
	@DeleteMapping("/{id}")
	public boolean delete(@PathVariable String id) {
		vecRoleRepository.delete(id);

		return true;
	}

	@ApiOperation(value = "Create a Group")
	@PostMapping
	public VecRole add(VecRole vecRole) {
		vecRoleRepository.save(vecRole);
		return vecRole;

	}
}
