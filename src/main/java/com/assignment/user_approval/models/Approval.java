package com.assignment.user_approval.models;



import lombok.Data;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity(name = "approvals")
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Approval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id" , referencedColumnName = "id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_id" , referencedColumnName = "id")
    private User user;

    @Column
    private String comment;
    @Column
    private boolean approved;
}
