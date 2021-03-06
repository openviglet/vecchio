package com.viglet.vecchio.api.context;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viglet.vecchio.persistence.model.app.VecMapping;
import com.viglet.vecchio.persistence.repository.app.VecMappingRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v2/mapping")
@Api(value = "/mapping", tags = "Mapping", description = "Mapping")
public class VecMappingAPI {
	@Autowired
	private VecMappingRepository vecMappingRepository;

	@ApiOperation(value = "Show Mapping List")
	@GetMapping
	public List<VecMapping> list() {
		return vecMappingRepository.findAll();
	}

	@ApiOperation(value = "Show a Mapping")
	@GetMapping("/{id}")
	public VecMapping edit(@PathVariable String id) {
		return vecMappingRepository.findById(id).get();
	}

	@ApiOperation(value = "Update a Mapping")
	@PutMapping("/{id}")
	public VecMapping update(@PathVariable String id, @RequestBody VecMapping vecMapping) {
		VecMapping vecMappingEdit = vecMappingRepository.findById(id).get();

		vecMappingEdit.setPattern(vecMapping.getPattern());
		vecMappingEdit.setUrl(vecMapping.getUrl());
		vecMappingRepository.save(vecMappingEdit);
		return vecMappingEdit;
	}

	@Transactional
	@ApiOperation(value = "Delete a Mapping")
	@DeleteMapping("/{id}")
	public boolean delete(@PathVariable String id) {
		vecMappingRepository.delete(id);
		return true;
	}

	@ApiOperation(value = "Create a Mapping")
	@PostMapping
	public VecMapping add(@RequestBody VecMapping vecMapping) {
		vecMappingRepository.save(vecMapping);
		return vecMapping;

	}
}
