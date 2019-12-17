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

package com.viglet.vecchio.onstartup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.viglet.vecchio.onstartup.app.VecAppOnStartup;
import com.viglet.vecchio.onstartup.app.VecMappingOnStartup;
import com.viglet.vecchio.onstartup.auth.VecGroupOnStartup;
import com.viglet.vecchio.onstartup.auth.VecRoleOnStartup;
import com.viglet.vecchio.onstartup.auth.VecUserOnStartup;
import com.viglet.vecchio.onstartup.system.VecConfigVarOnStartup;
import com.viglet.vecchio.onstartup.system.VecLocaleOnStartup;
import com.viglet.vecchio.persistence.repository.system.VecConfigVarRepository;

@Component
public class VecOnStartup implements ApplicationRunner {

	@Autowired
	private VecConfigVarRepository vecConfigVarRepository;
	@Autowired
	private VecLocaleOnStartup vecLocaleOnStartup;
	@Autowired
	private VecGroupOnStartup vecGroupOnStartup;
	@Autowired
	private VecUserOnStartup vecUserOnStartup;
	@Autowired
	private VecRoleOnStartup vecRoleOnStartup;
	@Autowired
	private VecAppOnStartup vecAppOnStartup;
	@Autowired
	private VecMappingOnStartup vecMappingOnStartup;
	@Autowired
	private VecConfigVarOnStartup vecConfigVarOnStartup;

	@Override
	public void run(ApplicationArguments arg0) throws Exception {

		if (!vecConfigVarRepository.existsByPathAndName(VecConfigVarOnStartup.FIRST_TIME_PATH,
				VecConfigVarOnStartup.FIRST_TIME_NAME)) {

			System.out.println("First Time Configuration ...");

			vecLocaleOnStartup.createDefaultRows();
			vecGroupOnStartup.createDefaultRows();
			vecUserOnStartup.createDefaultRows();
			vecRoleOnStartup.createDefaultRows();
			vecAppOnStartup.createDefaultRows();
			vecMappingOnStartup.createDefaultRows();
			vecConfigVarOnStartup.createDefaultRows();

			System.out.println("Configuration finished.");
		}

	}

}
