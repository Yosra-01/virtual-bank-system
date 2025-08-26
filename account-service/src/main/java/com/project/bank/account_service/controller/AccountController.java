package com.project.bank.account_service.controller;

import java.util.List;
import java.util.UUID;

import com.project.bank.account_service.model.Account;
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
//import com.project.bank.account_service.model.Account;
import com.project.bank.account_service.service.AccountService;
import com.project.bank.account_service.service.LoggingProducerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;



@RestController
public class AccountController {

    private final AccountService accountService;
    private final LoggingProducerService loggingProducer;

    public AccountController(AccountService accountService, LoggingProducerService loggingProducer){
        this.accountService = accountService;
        this.loggingProducer = loggingProducer;
    }


    //CREATE
    @Operation(summary = "Create a new account")
    @ApiResponse(responseCode ="201", description = "Account created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data") 

    @PostMapping("/accounts")

    public ResponseEntity<AccountResponse> createAccount(@RequestBody @Valid AccountRequest request) {
        loggingProducer.sendLog(request, "Request");
        
        AccountResponse newAccount = accountService.createAccount(request);

        loggingProducer.sendLog(newAccount, "Response");
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }




    //READ

    @Operation(summary = "Get Account by ID")
    @ApiResponse(responseCode = "200", description = "Account found")
    @ApiResponse(responseCode = "404", description = "Account not found")

    @GetMapping("/accounts/{accountId}")

    public ResponseEntity<AccountResponse> getAccount(@PathVariable UUID accountId) {
        loggingProducer.sendLog(accountId, "Request");

        AccountResponse account = accountService.getAccount(accountId);

        loggingProducer.sendLog(account, "Response");
        return new ResponseEntity<>(account, HttpStatus.OK);
    }


    @Operation(summary = "Get all user Accounts")
    @ApiResponse(responseCode = "200", description = "Account(s) found")
    @ApiResponse(responseCode = "404", description = "No Account found")

    @GetMapping("/users/{userId}/accounts")

    public ResponseEntity<List<AccountResponse>> getUserAccounts(@PathVariable UUID userId) {
        loggingProducer.sendLog(userId, "Request");

        List<AccountResponse> accounts = accountService.getUserAccounts(userId);

        loggingProducer.sendLog(accounts, "Response");
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }



    //UPDATE
    @Operation(summary = "Update employee by ID")
    @ApiResponse(responseCode = "200", description = "Account updated successfully")
    @ApiResponse(responseCode = "404", description = "Account not found")
    @ApiResponse(responseCode = "400", description = "Insufficient funds to make the transfer") //400 -> conflict between request and resource availability

    @PutMapping("/accounts/transfer")

    public ResponseEntity<String> transfer(@RequestBody TransferRequest request) {

        loggingProducer.sendLog(request, "Request");

         accountService.transfer(
            request.getAmount(),
            request.getSender(),
            request.getReceiver()
        );

        loggingProducer.sendLog("Successful Transfer", "Response");
        return ResponseEntity.ok("Successful Transfer");
    }

    //For scheduled job in transactions
    @GetMapping("/users/all/active-savings")
    public ResponseEntity<List<AccountResponse>> getActiveSavingsAccounts() {

        loggingProducer.sendLog(null , "Request");

        List<Account> accounts = accountService.getActiveSavingsAccounts();
        if (accounts.isEmpty()) return ResponseEntity.notFound().build();
        List<AccountResponse> out = accounts.stream()
                .map(AccountResponse::from)
                .toList();
        
        loggingProducer.sendLog(out, "Response");        
        return ResponseEntity.ok(out);
    }

}
