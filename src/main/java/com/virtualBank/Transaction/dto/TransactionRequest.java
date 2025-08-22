package com.virtualBank.Transaction.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransactionRequest {
    @NotBlank
    private String fromAccountId;
    @NotBlank
    private String toAccountId;
    @NotNull
    @Min(1)
    private Double amount;
    private String description;
}
