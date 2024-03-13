package com.example.taskmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TmUser;

public interface TaskRepository extends JpaRepository<Task, Integer> {

	List<Task> findByTitle(String title);

	void deleteById(Integer id);

	List<Task> findByOwner(TmUser user);

	Task findByIdAndOwner(int taskId, TmUser user);

	@Query("SELECT DISTINCT t FROM Task t " +
	           "LEFT JOIN t.collaborators c " +
	           "WHERE t.owner.id = :userId OR c.id = :userId")
    List<Task> findTasksForUser(@Param("userId") Integer userId);

}
