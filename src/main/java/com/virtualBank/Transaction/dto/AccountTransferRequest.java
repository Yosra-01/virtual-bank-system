package com.virtualBank.Transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransferRequest {
    private String fromAccountId;
    private String toAccountId;
    private Double amount;
}
