package com.assignment.user_approval.service;
import com.assignment.user_approval.models.Approval;
import com.assignment.user_approval.models.Status;
import com.assignment.user_approval.models.Task;
import com.assignment.user_approval.models.User;
import com.assignment.user_approval.repository.ApprovalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


//Use @RequiredArgsConstructor in Spring Boot services for dependency injection.
// Use it in JPA entities for required fields.
// Avoid @Autowired when using constructor-based injection.
@Service
@RequiredArgsConstructor //The @RequiredArgsConstructor annotation in Lombok generates a constructor with required arguments. It includes: @nonnull and final
public class ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final TaskService taskService;
    private final UserService userService;
    private final EmailService mailService;

    public Approval addApproval(User approver, Task task) {
        Approval approval = new Approval();
        approval.setApproved(false);
        approval.setComment("");
        approval.setUser(approver);
        approval.setTask(task);
        approvalRepository.save(approval);
        return approval;
    }


    // Method to approve a task
    public Approval approveTask(Long taskId, String usermail, String comment) {
        Task task = taskService.getTaskById(taskId);
        User approvingUser = userService.getUserByEmail(usermail);
        Approval approval = new Approval();
        approval.setTask(task);
        approval.setUser(approvingUser);
        approval.setComment(comment);
        approval.setApproved(Boolean.TRUE);
        approvalRepository.save(approval);
        return approval;
    }

    // Method to reject a task
    public Approval rejectTask(Long taskId, String usermail, String comment) {
        Task task = taskService.getTaskById(taskId);
        User approvingUser = userService.getUserByEmail(usermail);
        Approval approval = new Approval();
        approval.setTask(task);
        approval.setUser(approvingUser);
        approval.setComment(comment);
        approval.setApproved(Boolean.FALSE);
        return approvalRepository.save(approval);
    }

}

