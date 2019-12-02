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

package com.viglet.vecchio.onstartup.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.viglet.vecchio.persistence.model.app.VecMapping;
import com.viglet.vecchio.persistence.repository.app.VecMappingRepository;

@Component
public class VecMappingOnStartup {
	public final static String SAMPLE_MAPPING_ID = "2c3d7fda-2d32-428c-ab0b-a5040ee9a61b";
	@Autowired
	private VecMappingRepository vecMappingRepository;

	public void createDefaultRows() {

		if (vecMappingRepository.findAll().isEmpty()) {
			VecMapping vecMapping = new VecMapping();
			vecMapping.setId(SAMPLE_MAPPING_ID);
			vecMapping.setUrl("https://api.github.com/users/openviglet");
			vecMapping.setPattern("/proxy/github/openviglet");

			vecMappingRepository.saveAndFlush(vecMapping);
		}

	}
}
