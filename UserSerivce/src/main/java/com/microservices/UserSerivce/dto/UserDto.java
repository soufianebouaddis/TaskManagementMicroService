package com.microservices.UserSerivce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    @NotNull(message = "Nom cannot be null")
    String nom;
    @NotNull(message = "Prenom cannot be null")
    String prenom;
    @NotNull(message = "Email cannot be null")
    String email;
    @NotNull(message = "password cannot be null")
    String password;
    @NotNull(message = "username cannot be null")
    String username;

}
