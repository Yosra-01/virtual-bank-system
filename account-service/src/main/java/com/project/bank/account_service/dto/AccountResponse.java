package com.project.bank.account_service.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.project.bank.account_service.model.AccountStatus;
import com.project.bank.account_service.model.AccountType;


public class AccountResponse {
    private UUID accountId;
    private AccountType accountType;
    private String accountNumber;
    private BigDecimal balance;
    private AccountStatus status;

    public AccountResponse() {}
    public AccountResponse(UUID accountId, AccountType accountType, String accountNumber,
                           BigDecimal balance, AccountStatus status) {
        this.accountId = accountId;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
    }
    public UUID getAccountId() { return accountId; }
    public AccountType getAccountType() { return accountType; }
    public String getAccountNumber() { return accountNumber; }
    public BigDecimal getBalance() { return balance; }
    public AccountStatus getStatus() { return status; }

    public void setAccountId(UUID v) { this.accountId = v; }
    public void setAccountType(AccountType v) { this.accountType = v; }
    public void setAccountNumber(String v) { this.accountNumber = v; }
    public void setBalance(BigDecimal v) { this.balance = v; }
    public void setStatus(AccountStatus v) { this.status = v; }
}

