package com.example.taskmanager.service;

import java.util.List;

import com.example.taskmanager.model.TmUser;

public interface TmUserService {
	
	public TmUser createUser(TmUser user);

	public List<TmUser> getAllUsers();

	public TmUser authenticateUser(String email, String password);

	public boolean existsByEmail(String email);

	public TmUser getUserById(int userId);
	
	public TmUser getUserByEmail(String email);

	public List<Integer> lookupCollaboratorIdsByEmails(List<String> emails);
}
