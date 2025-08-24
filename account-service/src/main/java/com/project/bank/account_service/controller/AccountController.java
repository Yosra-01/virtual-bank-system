package com.project.bank.account_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.bank.account_service.dto.TransferRequest;
import com.project.bank.account_service.dto.AccountRequest;
import com.project.bank.account_service.dto.AccountResponse;
import com.project.bank.account_service.model.Account;
import com.project.bank.account_service.service.AccountService;
import com.project.bank.account_service.service.TransferStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;



@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }


    //CREATE
    @Operation(summary = "Create a new account")
    @ApiResponse(responseCode ="201", description = "Account created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data") //check <----

    @PostMapping("/accounts")

    public ResponseEntity<Account> createAccount( @Valid @RequestBody AccountRequest request) {
        
        if(request.getBalance().equals(null))
            throw new RuntimeException("400");

        Account newAccount = accountService.createAccount(request);
        
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }
    



    //READ

    @Operation(summary = "Get Account by ID")
    @ApiResponse(responseCode = "200", description = "Account found")
    @ApiResponse(responseCode = "404", description = "Account not found")

    @GetMapping("/accounts/{accountId}")

    public ResponseEntity<AccountResponse> getAccount(@PathVariable UUID accountId) {
           
        AccountResponse account = accountService.getAccount(accountId);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }


    @Operation(summary = "Get all user Accounts")
    @ApiResponse(responseCode = "200", description = "Account(s) found")
    @ApiResponse(responseCode = "404", description = "No Account found")
    @GetMapping("/users/{userId}/accounts")

    public ResponseEntity<List<AccountResponse>> getUserAccounts(@PathVariable UUID userId) {
        
        List<AccountResponse> accounts = accountService.getUserAccounts(userId);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }



    //UPDATE
    @Operation(summary = "Update employee by ID")
    @ApiResponse(responseCode = "200", description = "Account updated successfully")
    @ApiResponse(responseCode = "404", description = "Account not found")
    @ApiResponse(responseCode = "409", description = "Insufficient funds to make the transfer") //409 -> conflict between request and resource availability

    @PutMapping("/accounts/transfer")

    public ResponseEntity<String> transfer(@RequestBody TransferRequest request) {
        
        TransferStatus status = accountService.transfer(
            request.getAmount(),
            request.getSender(),
            request.getReceiver()    
        );
        
        return switch (status){
            case SUCCESS -> ResponseEntity.ok("Successful Transfer");
            case SENDER_NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sender Account not found"); 
            case RECEIVER_NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receiving Account not found");
            case INSUFFICIENT_FUNDS -> ResponseEntity.status(HttpStatus.CONFLICT).body("Insufficient funds");
        };
    }
    



    

}
