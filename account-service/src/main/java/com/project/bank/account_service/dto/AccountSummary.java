package com.project.bank.account_service.dto;

import lombok.Data;

@Data
public class AccountSummary {
    private String accountId;
    private String accountType; // SAVINGS, SYSTEM, etc.
    private String status;      // ACTIVE, INACTIVE
    private Double balance;
}
