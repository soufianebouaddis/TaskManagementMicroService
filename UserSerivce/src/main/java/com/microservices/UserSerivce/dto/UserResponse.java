package com.microservices.UserSerivce.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private int id;

    private String nom;

    private String prenom;

    private String email;

    private String password;

    private String username;

    List<TaskDto> tasks;

    public UserResponse(int id, String nom, String prenom, String email, String password, String username) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.username = username;
    }

}
