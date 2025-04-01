package com.assignment.user_approval.repository;

import com.assignment.user_approval.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepository extends JpaRepository<Task, Long> {
}
