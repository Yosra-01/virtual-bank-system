package com.project.bank.account_service.dto;

import java.math.BigDecimal;
import java.util.UUID;


public class AccountResponse {
    private UUID accountId;
    private String accountType;
    private String accountNumber;
    private BigDecimal balance;
    private String status;

    public AccountResponse() {}
    public AccountResponse(UUID accountId, String accountType, String accountNumber,
                           BigDecimal balance, String status) {
        this.accountId = accountId;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
    }
    public UUID getAccountId() { return accountId; }
    public String getAccountType() { return accountType; }
    public String getAccountNumber() { return accountNumber; }
    public BigDecimal getBalance() { return balance; }
    public String getStatus() { return status; }
    public void setAccountId(UUID v) { this.accountId = v; }
    public void setAccountType(String v) { this.accountType = v; }
    public void setAccountNumber(String v) { this.accountNumber = v; }
    public void setBalance(BigDecimal v) { this.balance = v; }
    public void setStatus(String v) { this.status = v; }
}

