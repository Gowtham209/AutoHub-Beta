package com.AutoHub.autohub_backend.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.AutoHub.autohub_backend.Entities.Users;
import com.AutoHub.autohub_backend.Repositories.UsersRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsersRepository userRepoObj;

	@Override
	public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
		Users user=userRepoObj.findByEmailId(emailId);
		System.out.println("Custom LoaderUserById:"+user);
		if(user==null)
		{
			throw new UsernameNotFoundException("User Not Found 404");
		}
		return new UserPrincipleDetailsImpl(user);
	}
	
	public UserDetails loadUserByUserId(Long userId) throws UsernameNotFoundException {
		Users user=userRepoObj.findById(userId).orElse(null);
		System.out.println("Custom LoaderUserById:"+user);
		if(user==null)
		{
			throw new UsernameNotFoundException("User Not Found 404");
		}
		return new UserPrincipleDetailsImpl(user);
	}
	
	public Users loadUserByUsername(Users obj) throws UsernameNotFoundException {
		Users user=userRepoObj.findByEmailId(obj.getEmailId());
		System.out.println("userDetail:\n"+user);
		System.out.println("Custom LoaderUserById:"+user);
		if(user==null)
		{
			throw new UsernameNotFoundException("User Not Found 404");
		}
		return user;
	}

}

