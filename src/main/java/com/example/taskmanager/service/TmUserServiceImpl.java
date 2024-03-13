package com.example.taskmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.taskmanager.model.TmUser;
import com.example.taskmanager.repository.TmUserRepository;

@Service
public class TmUserServiceImpl implements TmUserService {
    
    @Autowired
    private TmUserRepository userRepo;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    public TmUserServiceImpl(TmUserRepository userRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public TmUser createUser(TmUser user) {
        try {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            return userRepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create user: " + e.getMessage());
        }
    }
    
    @Override
    public List<TmUser> getAllUsers() {
        try {
            return userRepo.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve users: " + e.getMessage());
        }
    }
    
    @Override
    public TmUser authenticateUser(String email, String password) {
        try {
            TmUser user = userRepo.findByEmail(email);
            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
            throw new RuntimeException("Invalid email or password");
        } catch (Exception e) {
            throw new RuntimeException("Failed to authenticate user: " + e.getMessage());
        }
    }
    
    @Override
    public boolean existsByEmail(String email) {
        try {
            return userRepo.existsByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException("Error checking email existence: " + e.getMessage());
        }
    }
    
    @Override
    public TmUser getUserById(int id) {
        try {
            return userRepo.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user: " + e.getMessage());
        }
    }
    
    @Override
    public TmUser getUserByEmail(String email) {
        try {
            return userRepo.findByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user by email: " + e.getMessage());
        }
    }
    
    @Override
    public List<Integer> lookupCollaboratorIdsByEmails(List<String> emails) {
        try {
            return userRepo.findCollaboratorIdsByEmails(emails);
        } catch (Exception e) {
            throw new RuntimeException("Failed to lookup collaborator IDs: " + e.getMessage());
        }
    }
}
