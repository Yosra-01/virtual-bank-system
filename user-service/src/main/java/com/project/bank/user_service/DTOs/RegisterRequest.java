package com.project.bank.user_service.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank @Size(min=3 , max=50)
    public String username;

    @NotBlank @Size(min = 8, max = 100)
    public String password;

    @NotBlank @Email
    public String email;


    @NotBlank @Size(min = 1, max = 60)
    public String firstName;

    @NotBlank @Size(min = 1, max = 60)
    public String lastName;
}
