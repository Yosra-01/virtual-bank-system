package com.project.bank.account_service.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.project.bank.account_service.model.AccountType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AccountRequest {

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Account type is required")
    private AccountType accountType;

    @DecimalMin(value = "0.00", inclusive = true, message = "Balance cannot be less than zero")
    @NotNull(message = "balance is required")
    private BigDecimal balance;

    @NotNull(message = "User ID is required")
    private UUID userId;
    
    
    ///////////in case lombok doesn't work////////

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


}

