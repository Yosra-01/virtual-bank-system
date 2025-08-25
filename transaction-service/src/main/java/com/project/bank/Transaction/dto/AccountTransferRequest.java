package com.project.bank.Transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransferRequest {
    private BigDecimal amount;
    private UUID sender;
    private UUID receiver;
}
