package com.assignment.user_approval.models;


import lombok.Data;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@Entity(name = "tasks")
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status = Status.CREATED;

    @ManyToOne  //Indicates that many records in this entity can be associated with one record in another entity (User).
    @JoinColumn(name = "owner_id", referencedColumnName = "id") // Specifies that the column "owner" in this entity's database table is a foreign key referencing the User table.
    private User owner;

}
