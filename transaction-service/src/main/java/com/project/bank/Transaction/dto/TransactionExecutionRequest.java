package com.project.bank.Transaction.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TransactionExecutionRequest {
    @NotBlank
    private String transactionId;

    public TransactionExecutionRequest(String transactionId) {
    }
}
