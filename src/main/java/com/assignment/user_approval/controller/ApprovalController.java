package com.assignment.user_approval.controller;


import com.assignment.user_approval.models.Approval;
import com.assignment.user_approval.service.ApprovalService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/approvals")
public class ApprovalController {

    private final ApprovalService approvalService;

    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    // Endpoint to approve a task by an approver
    @PostMapping("/{taskId}/approve")
    public ResponseEntity<?> approveTask(@PathVariable Long taskId, @RequestBody Map<String, String> request) {
        String usermail = request.get("usermail");
        String comment = request.get("comment");

        Approval approval = approvalService.approveTask(taskId, usermail, comment);
        return ResponseEntity.ok(Map.of("message", "Task approved successfully", "task", approval.getTask().getTitle()));
    }

    // Endpoint to reject a task by an approver
    @PostMapping("/{taskId}/reject")
    public ResponseEntity<?> rejectTask(@PathVariable Long taskId, @RequestBody Map<String, String> request) {
        String usermail = request.get("usermail");
        String comment = request.get("comment");

        Approval approval = approvalService.rejectTask(taskId, usermail, comment);
        return ResponseEntity.ok(Map.of("message", "Task rejected successfully", "task", approval.getTask().getTitle()));
    }
}

