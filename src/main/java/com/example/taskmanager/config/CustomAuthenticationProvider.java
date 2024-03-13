package com.example.taskmanager.config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.taskmanager.model.TmUser;
import com.example.taskmanager.repository.TmUserRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider  {
	
    @Autowired
	private TmUserRepository tmUserRepo;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        TmUser user = tmUserRepo.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())){
            return new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>());
        } else {
            return null; 
        }
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
