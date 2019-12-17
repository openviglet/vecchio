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

package com.viglet.vecchio.spring.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.viglet.vecchio.persistence.model.auth.VecUser;

public class VecCustomUserDetails extends VecUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	private List<String> vecUserRoles;

	public VecCustomUserDetails(VecUser vecUser, List<String> vecUserRoles) {
		super(vecUser);
		this.vecUserRoles = vecUserRoles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		String roles = StringUtils.collectionToCommaDelimitedString(vecUserRoles);
		return AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getUsername() {
		return super.getUsername();
	}

}
