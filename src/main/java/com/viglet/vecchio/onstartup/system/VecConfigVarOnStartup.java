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

package com.viglet.vecchio.onstartup.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.viglet.vecchio.persistence.model.system.VecConfigVar;
import com.viglet.vecchio.persistence.repository.system.VecConfigVarRepository;

@Component
public class VecConfigVarOnStartup {
	public static final String FIRST_TIME_PATH = "/system";
	public static final String FIRST_TIME_NAME = "FIRST_TIME";
	
	@Autowired
	private VecConfigVarRepository vecConfigVarRepository;

	public void createDefaultRows() {

		VecConfigVar vecConfigVar = new VecConfigVar();

		if (!vecConfigVarRepository.existsByPathAndName(FIRST_TIME_PATH, FIRST_TIME_NAME)) {

			vecConfigVar.setPath(FIRST_TIME_PATH);
			vecConfigVar.setName(FIRST_TIME_NAME);
			vecConfigVar.setValue("true");
			vecConfigVarRepository.save(vecConfigVar);

			vecConfigVar = new VecConfigVar();
	
		}
	}

}
