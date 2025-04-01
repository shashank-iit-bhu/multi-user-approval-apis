package com.assignment.user_approval.controller;

import com.assignment.user_approval.models.Task;
import com.assignment.user_approval.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Endpoint to create a task
    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody Map<String, Object> request) {
        String taskName = (String) request.get("taskName");
        String ownerMail = (String) request.get("ownerMail");
            List<String> approversMail = (List<String>) request.get("approversMail");

        Task newTask = taskService.createTask(taskName, ownerMail, approversMail);
        return ResponseEntity.ok(Map.of("message", "Task created successfully", "task", newTask));
    }

    // Endpoint to get all tasks
    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    // Endpoint to get task by ID
    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }
}
