package com.project.bank.Transaction.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogMessage {
    private Object message;      // JSON request/response
    private String messageType;  // "Request" or "Response"
    private LocalDateTime timestamp;     // ISO timestamp
    private String serviceName;

}
