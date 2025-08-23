package com.project.bank.account_service.model;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore //invisible to client 
    private UUID accountId; //uuid ensures uniqueness without needing a central ID generator. int/lomg can run out/collide
    
    @JsonIgnore
    private String accountNumber; //String avoids number formatting problems

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Account type is required")
    private AccountType accountType;
    
    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0.00", inclusive = true, message = "Balance cannot be less than zero")
    private BigDecimal balance; //precise w/o rounding errors
    
    @JsonIgnore
    private String status;
    
    //foreign key to user service
    @NotNull(message = "User ID is required")
    private UUID userID; 

}
