package com.project.bank.account_service.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.project.bank.account_service.model.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {

    private AccountType accountType;
    private BigDecimal balance;
    private UUID userId;

}
