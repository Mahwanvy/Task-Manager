package com.example.taskmanager.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.taskmanager.model.TmUser;

public class CustomUser implements UserDetails{
	
	private TmUser tmUser;
	
	public CustomUser(TmUser tmUser) {
		super();
		this.tmUser = tmUser;
	}

	private static final long serialVersionUID = 1L;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(tmUser.getRole());
		return Arrays.asList(authority);
	}

	@Override
	public String getPassword() {
		return tmUser.getPassword();
	}

	@Override
	public String getUsername() {
		return tmUser.getEmail();
	}

}
