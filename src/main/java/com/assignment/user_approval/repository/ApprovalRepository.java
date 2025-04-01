package com.assignment.user_approval.repository;

import com.assignment.user_approval.models.Task;
import com.assignment.user_approval.models.Approval;
import com.assignment.user_approval.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

// Hibernate is a java framework and ORM (Object Relation Mapping)
// tool that is used to provide the implementation of the JPA methods.

//JPA is an abstraction that is used to map the java object with the database.
// It contains different structures of the methods that are used for manipulating the table record
// and also provides the SQL queries for specific operations.

public interface ApprovalRepository extends JpaRepository<Approval, UUID> {

    List<Approval> findByTask(Task task);
}
