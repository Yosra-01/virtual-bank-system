package com.project.bank.user_service.DTOs;

import java.util.UUID;

public class RegisterResponse {
    public UUID userId;
    public String username;
    public String message;

    public RegisterResponse(UUID userId, String username, String message) {
        this.userId = userId;
        this.username = username;
        this.message = message;
    }

}
