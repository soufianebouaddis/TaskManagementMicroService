package com.microservices.UserSerivce.entity;

import java.util.List;

import com.microservices.UserSerivce.dto.TaskDto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@Table(name = "t_user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String prenom;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(unique = true, nullable = false)
    private String username;
    @ElementCollection
    List<Integer> tasks;
}
