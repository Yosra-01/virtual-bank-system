package com.project.bank.bff_service.controller;

import com.project.bank.bff_service.clients.AccountClient;
import com.project.bank.bff_service.clients.TransactionClient;
import com.project.bank.bff_service.clients.UserClient;
import com.project.bank.bff_service.dto.AccountDto;
import com.project.bank.bff_service.dto.TransactionDto;
import com.project.bank.bff_service.dto.UserDto;
//import com.project.bank.bff_service.dto.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestUserProfileController {

    private final UserClient userClient;
    private final AccountClient accountClient;
    private final TransactionClient txClient;
    @GetMapping("/user/{userId}/profile")
    public UserDto profile(@PathVariable UUID userId) {
        // Your user-service requires X-User-Id to equal the path id
        return userClient.getUserProfile(userId, userId.toString());
    }

    @GetMapping("/users/{userId}/accounts")
    public List<AccountDto> accounts(@PathVariable UUID userId) {
        return accountClient.getAccountsByUser(userId);
    }
    @GetMapping("/transactions/accounts/{accountId}/transactions")
    public List<TransactionDto> byAccount(@PathVariable String accountId) {
        List<TransactionDto> list = txClient.getHistory(accountId);
        return list != null ? list : List.of();
    }
}
