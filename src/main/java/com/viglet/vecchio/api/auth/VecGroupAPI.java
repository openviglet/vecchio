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

import com.viglet.vecchio.persistence.model.auth.VecGroup;
import com.viglet.vecchio.persistence.repository.auth.VecGroupRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v2/group")
@Api(value = "/group", tags = "Group", description = "Group")
public class VecGroupAPI {

	@Autowired
	private VecGroupRepository vecGroupRepository;

	@ApiOperation(value = "Show Group List")
	@GetMapping
	public List<VecGroup> list(){
		return vecGroupRepository.findAll();
	}
	
	@ApiOperation(value = "Show a Group")
	@GetMapping("/{id}")
	public VecGroup edit(@PathVariable String id){
		return vecGroupRepository.findById(id).get();
	}
	
	@ApiOperation(value = "Update a Group")
	@PutMapping("/{id}")
	public VecGroup update(@PathVariable String id, VecGroup vecGroup) throws Exception {
		VecGroup vecGroupEdit = vecGroupRepository.findById(id).get();
		vecGroupEdit.setName(vecGroup.getName());
		vecGroupEdit.setDescription(vecGroup.getDescription());
		vecGroupRepository.save(vecGroupEdit);
		return vecGroupEdit;
	}

	@ApiOperation(value = "Delete a Group")
	@DeleteMapping("/{id}")
	public boolean delete(@PathVariable String id) throws Exception {
		vecGroupRepository.delete(id);
		return true;
	}

	@ApiOperation(value = "Create a Group")
	@PostMapping
	public VecGroup add(VecGroup vecGroup) throws Exception {
		vecGroupRepository.save(vecGroup);
		return vecGroup;

	}
}
