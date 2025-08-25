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
    
    
    ///////////in case lombok doesn't work////////
    /* 
    public AccountRequest(AccountType accountType, BigDecimal balance, UUID userId) {
        this.accountType = accountType;
        this.balance = balance;
        this.userId = userId;
    }

    public AccountRequest() {
    }

    public UUID getUserId() { return userId; }
    public AccountType getAccountType() { return accountType; }
    public BigDecimal getBalance() { return balance; }
    

    public void setUserId(UUID v) {userId = v; }
    public void setAccountType(AccountType v) { this.accountType = v; }
    public void setBalance(BigDecimal v) { this.balance = v; }
    */

}

