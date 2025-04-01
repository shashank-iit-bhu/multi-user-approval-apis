package com.assignment.user_approval.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity  //The @Entity annotation in Spring Boot is used to mark a Java class as a JPA entity. This means the class is mapped to a database table, and its instances represent rows in that table.
@Table(name = "users")
@NoArgsConstructor //Generates a default (empty) constructor, Required for JPA entities because Hibernate needs a default constructor.
@AllArgsConstructor // Generates a constructor with all fields as parameters, useful for creating objects
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //Ensures global uniqueness (important for distributed systems), Avoids exposing sequential primary keys (security benefit).
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String name;


}
