package com.virtualBank.Transaction.controller;

import com.virtualBank.Transaction.dto.TransactionExecutionRequest;
import com.virtualBank.Transaction.dto.TransactionRequest;
import com.virtualBank.Transaction.dto.TransactionResponse;
import com.virtualBank.Transaction.entity.TransactionEntity;
import com.virtualBank.Transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @PostMapping("/transfer/initiation")
    public ResponseEntity<TransactionResponse> initiate(@Valid @RequestBody TransactionRequest req) {
        return ResponseEntity.ok(service.initiate(req));
    }

    @PostMapping("/transfer/execution")
    public ResponseEntity<TransactionResponse> execute(@Valid @RequestBody TransactionExecutionRequest req) {
        return ResponseEntity.ok(service.execute(req));
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public ResponseEntity<List<TransactionEntity>> history(@PathVariable String accountId) {
        return ResponseEntity.ok(service.getHistory(accountId));
    }
}
