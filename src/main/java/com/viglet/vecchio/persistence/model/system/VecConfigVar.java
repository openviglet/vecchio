/*
 * Copyright (C) 2016-2018 Alexandre Oliveira <alexandre.oliveira@viglet.com> 
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

package com.viglet.vecchio.persistence.model.system;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the vecConfigVar database table.
 * 
 */
@Entity
@Table(name = "vecConfigVar")
@NamedQuery(name = "VecConfigVar.findAll", query = "SELECT cv FROM VecConfigVar cv")
public class VecConfigVar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "UUID", strategy = "com.viglet.vecchio.jpa.VecUUIDGenerator")
	@GeneratedValue(generator = "UUID")

	@Column(name = "id", updatable = false, nullable = false)
	private String id;

	@Column(nullable = true, length = 255)
	private String path;

	@Column(nullable = true, length = 255)
	private String name;
	
	@Column(nullable = true, length = 255)
	private String value;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


}
