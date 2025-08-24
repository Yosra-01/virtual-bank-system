package com.project.bank.user_service.DTOs;

import java.util.UUID;

public class ProfileResponse {
    public UUID userId;
    public String username;
    public String email;
    public String firstName;
    public String lastName;

    public ProfileResponse(UUID userId, String username, String email, String firstName, String lastName) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
