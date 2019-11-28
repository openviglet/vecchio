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

import com.viglet.vecchio.persistence.model.auth.VecUser;
import com.viglet.vecchio.persistence.repository.auth.VecUserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/user")
@Api(value = "/user", tags = "User", description = "User")
public class VecUserAPI {
	@Autowired
	private VecUserRepository vecUserRepository;

	@ApiOperation(value = "Show User List")
	@GetMapping
	public List<VecUser> list() {
		return vecUserRepository.findAll();
	}

	@ApiOperation(value = "Show a user")
	@GetMapping("/{id}")
	public VecUser edit(@PathVariable String id) {
		return vecUserRepository.findById(id).get();
	}

	@ApiOperation(value = "Update a User")
	@PutMapping("/{id}")
	public VecUser update(@PathVariable String id, VecUser vecUser) {
		VecUser vecUserEdit = vecUserRepository.findById(id).get();
		vecUserEdit.setConfirmEmail(vecUser.getConfirmEmail());
		vecUserEdit.setEmail(vecUser.getEmail());
		vecUserEdit.setFirstName(vecUser.getFirstName());
		vecUserEdit.setLastLogin(vecUser.getLastLogin());
		vecUserEdit.setLastName(vecUser.getLastName());
		vecUserEdit.setLoginTimes(vecUser.getLoginTimes());
		vecUserEdit.setPassword(vecUser.getPassword());
		vecUserEdit.setRealm(vecUser.getRealm());
		vecUserEdit.setRecoverPassword(vecUser.getRecoverPassword());
		vecUserEdit.setUsername(vecUser.getUsername());

		vecUserRepository.save(vecUserEdit);
		return vecUserEdit;
	}

	@ApiOperation(value = "Delete a User")
	@DeleteMapping("/{id}")
	public boolean delete(@PathVariable String id) {
		vecUserRepository.delete(id);
		return true;
	}

	@ApiOperation(value = "Create a User")
	@PostMapping
	public VecUser add(VecUser vecUser) throws Exception {
		vecUserRepository.save(vecUser);
		return vecUser;

	}
}
