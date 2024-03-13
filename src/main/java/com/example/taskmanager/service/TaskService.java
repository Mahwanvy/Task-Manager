package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TmUser;

import java.util.List;

public interface TaskService {
    
    Task getTaskById(int id);
    
	List<Task> getTasksForUser(TmUser user);
	
	Task updateTaskForUser(TmUser user, int taskId, Task task);
	
	void deleteTaskForUser(TmUser user, int taskId);
	
	Task createTaskForUser(TmUser user, Task task, List<TmUser> collaborators);
	
	List<Task> getAllTasksForUser(TmUser user);
    
}
