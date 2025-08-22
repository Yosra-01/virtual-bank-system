package com.project.bank.account_service.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.bank.account_service.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;






@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    //CREATE
    @Operation(summary = "Create a new employee")
    @ApiResponse(responseCode ="201", description = "Account created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")

    @PostMapping("/accounts")

    public String createAccount(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }
    



    //READ

    @Operation(summary = "Get Account by ID")
    @ApiResponse(responseCode = "200", description = "Account found")
    @ApiResponse(responseCode = "404", description = "Account not found")

    @GetMapping("accounts/{accountId}")

    public String getAccount(String param) {
        return new String();
    }


    @Operation(summary = "Get all user Accounts")
    @ApiResponse(responseCode = "200", description = "Account(s) found")
    @ApiResponse(responseCode = "404", description = "No Account found")
   // @ApiResponse(responseCode = "400", description = "User does not exist") wla user microservice issue???
    @GetMapping("/users/{userId}/accounts")

    public String getUserAccounts(String param) {
        return new String();
    }
    



    //UPDATE
    @Operation(summary = "Update employee by ID")
    @ApiResponse(responseCode = "200", description = "Account updated successfully")
    @ApiResponse(responseCode = "404", description = "Account not found")
    @ApiResponse(responseCode = "409", description = "Insufficient funds to make the transfer") //409 conflict between request and resource availability

    @PutMapping("accounts/transfer")

    public String transfer(@PathVariable String id, @RequestBody String entity) {
        //TODO: process PUT request
        
        return entity;
    }
    



    

}
