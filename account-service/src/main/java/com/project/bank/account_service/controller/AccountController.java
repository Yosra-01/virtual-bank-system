package com.project.bank.account_service.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.bank.account_service.service.AccountService;






@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    //CREATE
    @PostMapping("/accounts")

    public String createAccount(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }
    



    //READ
    @GetMapping("accounts/{accountId}")

    public String getAccount(String param) {
        return new String();
    }


    
    @GetMapping("/users/{userId}/accounts")

    public String getUserAccounts(String param) {
        return new String();
    }
    



    //UPDATE
    @PutMapping("accounts/transfer")

    public String transfer(@PathVariable String id, @RequestBody String entity) {
        //TODO: process PUT request
        
        return entity;
    }
    



    

}
