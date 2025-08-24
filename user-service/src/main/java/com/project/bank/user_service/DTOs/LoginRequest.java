package com.project.bank.user_service.DTOs;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank
    public String username;

    @NotBlank
    public String password;
}
