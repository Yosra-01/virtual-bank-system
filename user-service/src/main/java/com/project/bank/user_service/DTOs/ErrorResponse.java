package com.project.bank.user_service.DTOs;

import java.time.Instant;

public class ErrorResponse {
    public int status;            // 400, 401, 404, 409, 500
    public String error;          // "Bad Request", "Conflict", ...
    public String message;
    public Instant timestamp = Instant.now();

    public ErrorResponse(int status, String error, String message, String path) {
        this.status = status; this.error = error; this.message = message;
    }
}
