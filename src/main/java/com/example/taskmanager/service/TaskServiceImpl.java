package com.example.taskmanager.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TmUser;
import com.example.taskmanager.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task getTaskById(int id) {
        try {
            Optional<Task> optionalTask = taskRepository.findById(id);
            return optionalTask.orElseThrow(() -> new RuntimeException("Task not found with ID: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve task by ID: " + id, e);
        }
    }

    @Override
    public List<Task> getTasksForUser(TmUser user) {
        try {
            return taskRepository.findByOwner(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve tasks for user: " + user.getId(), e);
        }
    }

    @Override
    public List<Task> getAllTasksForUser(TmUser user) {
        try {
            Integer userId = user.getId();
            return taskRepository.findTasksForUser(userId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all tasks for user: " + user.getId(), e);
        }
    }

    @Override
    public Task updateTaskForUser(TmUser user, int taskId, Task task) {
        try {
            Task existingTask = getTaskById(taskId);
                existingTask.setTitle(task.getTitle());
                existingTask.setDescription(task.getDescription());
                existingTask.setDueDate(task.getDueDate());
                existingTask.setStatus(task.getStatus());
                existingTask.setCollaborators(task.getCollaborators());
                return taskRepository.save(existingTask);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update task for user: " + user.getId() + ", task ID: " + taskId, e);
        }
    }

    @Override
    public void deleteTaskForUser(TmUser user, int taskId) {
        try {
            Task existingTask = taskRepository.findByIdAndOwner(taskId, user);
            if (existingTask != null) {
                taskRepository.delete(existingTask);
            } else {
                throw new RuntimeException("Task not found or unauthorized to delete with ID: " + taskId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete task for user: " + user.getId() + ", task ID: " + taskId, e);
        }
    }

    @Override
    public Task createTaskForUser(TmUser user, Task task, List<TmUser> collaborators) {
        try {
            task.setOwner(user);
            task.setCollaborators(collaborators);
            return taskRepository.save(task);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create task for user: " + user.getId(), e);
        }
    }

}
