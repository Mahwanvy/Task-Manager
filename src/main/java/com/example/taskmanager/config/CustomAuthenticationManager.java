package com.example.taskmanager.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.taskmanager.model.TmUser;
import com.example.taskmanager.repository.TmUserRepository;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

	 @Autowired
		private TmUserRepository tmUserRepo;
	    
	    @Autowired
	    private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
        
        TmUser user = tmUserRepo.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
        	return new UsernamePasswordAuthenticationToken(email, password, grantedAuths);
        } else {
            return null; 
        }
	}

}
