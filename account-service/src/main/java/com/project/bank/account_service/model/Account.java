package com.project.bank.account_service.model;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
    private UUID accountId; //uuid ensures uniqueness without needing a central ID generator. int/lomg can run out/collide
    
    private String accountNumber; //String avoids number formatting problems

    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    
    private BigDecimal balance; //precise w/o rounding errors
    
    //active/inactive
    private String status;
    
    //foreign key to user service
    private UUID userID; 

}
