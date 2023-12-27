package com.microservices.UserSerivce.RequestResponse;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
    @NotNull(message = "username cannot be null")
    private String username;
    @NotNull(message = "password cannot be null")
    private String password;
}