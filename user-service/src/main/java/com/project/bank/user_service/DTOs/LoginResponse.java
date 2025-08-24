package com.project.bank.user_service.DTOs;

import java.util.UUID;

public class LoginResponse {
    public UUID userId;
    public String username;

    public LoginResponse(UUID userId, String username) {
        this.userId = userId;
        this.username = username;
    }
}
