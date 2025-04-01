package com.assignment.user_approval.service;


import com.assignment.user_approval.models.Status;
import com.assignment.user_approval.models.Task;
import com.assignment.user_approval.models.User;
import com.assignment.user_approval.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final EmailService emailService ;

    public TaskService(TaskRepository taskRepository, UserService userService, EmailService emailService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.emailService = emailService;
    }

    // Method to create a task
    public Task createTask(String taskName, String ownerMail, List<String> approversMail) {
        if (approversMail == null || approversMail.size() != 3) {
            throw new RuntimeException("Exactly 3 approvers are required.");
        }

        User createdBy = userService.getUserByEmail(ownerMail);
        List<User> approvers = userService.findAllByEmail(approversMail);
        Task task = new Task();
        task.setTitle(taskName);
        task.setStatus(Status.CREATED); // Task is in pending status when created
        task.setOwner(createdBy);
        taskRepository.save(task);

//        for(User approver: approvers){
//            approvalService.addApproval(approver, task);
//        }
        emailService.sendEmailToMultipleRecipients(approversMail,"Please approve the task", "");
        return task;
    }

    // Method to get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Method to get task by ID
    public Task getTaskById(Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        return task.orElseThrow(() -> new RuntimeException("Task not found"));
    }

    // Method to check if all approvals are done
    private boolean checkTaskCompletion(Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        return task.get().getStatus().equals(Status.APPROVED);
    }

    private void taskDone(Long taskId) {
         Optional<Task> task = taskRepository.findById(taskId);
         Task currTask = task.get();
          currTask.setStatus(Status.APPROVED);
         taskRepository.save(currTask);
    }
}
