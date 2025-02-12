package com.AutoHub.autohub_backend.SecurityConfig;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.AutoHub.autohub_backend.Entities.Users;
import com.AutoHub.autohub_backend.Utility.Utility;

public class UserPrincipleDetailsImpl implements UserDetails{
	
	private Users user;
	UserPrincipleDetailsImpl(Users userObj)
	{
		this.user=userObj;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Integer role=user.getUserRole();
		if(role==1)
		{
		return Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		else
		{
			return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
		}
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmailId();
	}

	public Long getUserId()
	{
		return user.getUserId();
	}
}
