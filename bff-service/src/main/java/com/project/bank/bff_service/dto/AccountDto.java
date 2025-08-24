package com.project.bank.bff_service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AccountDto {
    private UUID accountId;      // matches Account.getAccountId()
    private String accountType;  // matches Account.getAccountType()
    private String accountNumber; // if your model has it; otherwise remove
    private BigDecimal balance;  // matches Account.getBalance()
    private String status;
}

