package com.project.bank.Transaction.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TransactionResponse {
    private String transactionId;
    private String status;
    private LocalDateTime timestamp;
}
