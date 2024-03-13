package com.example.taskmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.taskmanager.model.TmUser;

public interface TmUserRepository extends JpaRepository<TmUser, Integer>{

	public TmUser findByEmail(String email);

	public boolean existsByEmail(String email);
	
	TmUser findById(int id);

	@Query("SELECT u.id FROM TmUser u WHERE u.email IN :emails")
	public List<Integer> findCollaboratorIdsByEmails(List<String> emails);
}
