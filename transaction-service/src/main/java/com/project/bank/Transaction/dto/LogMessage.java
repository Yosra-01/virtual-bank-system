package com.project.bank.Transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogMessage {
    private String message;      // JSON request/response
    private String messageType;  // "Request" or "Response"
    private String dateTime;     // ISO timestamp
}
