package com.example.taskmanager.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TmUser;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.service.TmUserService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private TmUserService userService;
    

    @GetMapping("/{userId}") 
    public List<Task> getAllTasksForUser(@PathVariable int userId) {
        TmUser user = userService.getUserById(userId);
        return taskService.getAllTasksForUser(user);
    }
    
    @PostMapping
    public Task createTaskForUser(
            @RequestParam("userId") int userId,
            @RequestBody Task task) {
        TmUser user = userService.getUserById(userId);
        if (user != null) {
        	List<TmUser> collaborators = task.getCollaborators();
            return taskService.createTaskForUser(user, task, collaborators);
            
        } else {
            return null;
        }
    }

    @PutMapping("/{userId}/{taskId}") 
    public Task updateTaskForUser(@PathVariable int userId, @PathVariable int taskId, @RequestBody Task task) {
        TmUser user = userService.getUserById(userId);
        return taskService.updateTaskForUser(user, taskId, task);
    }

    @DeleteMapping("/{userId}/{taskId}") 
    public void deleteTaskForUser(@PathVariable int userId, @PathVariable int taskId) {
        TmUser user = userService.getUserById(userId);
        taskService.deleteTaskForUser(user, taskId);
    } 

    
}
