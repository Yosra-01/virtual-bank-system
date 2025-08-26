package com.project.bank.account_service.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogMessage {

    private Object message;
    private String messageType; //"Request" or "Response"
    private LocalDateTime timestamp;
    private String serviceName;

}
