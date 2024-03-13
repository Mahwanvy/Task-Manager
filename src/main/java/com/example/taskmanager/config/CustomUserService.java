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
	
	
	@Autowired
	public CustomUserService(TmUserRepository tmUserRepo, BCryptPasswordEncoder passwordEncoder) {
		super();
		this.tmUserRepo = tmUserRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		TmUser tmuser=tmUserRepo.findByEmail(username);
		if (tmuser==null) {
			throw new UsernameNotFoundException("Username not found");
		}else {
			return new CustomUser(tmuser);
		}
	}

	public TmUser authenticateUser(String email, String password) {
        TmUser user = tmUserRepo.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
		return null;
	}

}
