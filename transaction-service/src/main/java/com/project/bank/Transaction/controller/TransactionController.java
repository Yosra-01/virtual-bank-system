package com.project.bank.Transaction.controller;

import com.project.bank.Transaction.dto.TransactionExecutionRequest;
import com.project.bank.Transaction.dto.TransactionRequest;
import com.project.bank.Transaction.dto.TransactionResponse;
import com.project.bank.Transaction.entity.TransactionEntity;
import com.project.bank.Transaction.service.LoggingProducer;
import com.project.bank.Transaction.service.TransactionService;
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
    private final LoggingProducer loggingProducer;

    @PostMapping("/transfer/initiation")
    public ResponseEntity<TransactionResponse> initiate(@Valid @RequestBody TransactionRequest req) {
        loggingProducer.sendLog(req.toString(), "Request");
        TransactionResponse response = service.initiate(req);
        loggingProducer.sendLog(response.toString(), "Response");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/transfer/execution")
    public ResponseEntity<TransactionResponse> execute(@Valid @RequestBody TransactionExecutionRequest req) {
        loggingProducer.sendLog(req.toString(), "Request");
        TransactionResponse response = service.execute(req);
        loggingProducer.sendLog(response.toString(), "Response");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public ResponseEntity<List<TransactionEntity>> history(@PathVariable String accountId) {
        loggingProducer.sendLog("AccountId: " + accountId, "Request");
        List<TransactionEntity> transactions = service.getHistory(accountId);
        loggingProducer.sendLog(transactions.toString(), "Response");
        return ResponseEntity.ok(transactions);
    }
}
