package com.project.bank.bff_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDto {
    private String transactionId;
    private String fromAccountId;
    private String toAccountId;
    private Double amount;          // matches service entity; we can switch to BigDecimal later
    private String description;
    private String status;          // enum serialized as STRING
    private LocalDateTime timestamp;
}
