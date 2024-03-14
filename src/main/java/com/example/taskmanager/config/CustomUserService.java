package com.example.taskmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.taskmanager.model.TmUser;
import com.example.taskmanager.repository.TmUserRepository;

@Component
public class CustomUserService implements UserDetailsService {
	
	@Autowired
	private TmUserRepository tmUserRepo;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		TmUser tmuser=tmUserRepo.findByEmail(email);
		if (tmuser==null) {
			throw new UsernameNotFoundException("Username not found");
		}else {
			return new CustomUser(tmuser);
		}
	}

}
