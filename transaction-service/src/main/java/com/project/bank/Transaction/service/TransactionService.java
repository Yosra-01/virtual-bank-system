package com.project.bank.Transaction.service;

import com.project.bank.Transaction.dto.*;
import com.project.bank.Transaction.entity.TransactionEntity;
import com.project.bank.Transaction.entity.TransactionStatus;
import com.project.bank.Transaction.exception.BadRequestException;
import com.project.bank.Transaction.exception.NotFoundException;
import com.project.bank.Transaction.mapper.TransactionMapper;
import com.project.bank.Transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository repo;
    private final TransactionMapper mapper;
    private final RestTemplate restTemplate;

    @Value("${account.service.base-url:http://localhost:8084}")
    private String accountServiceBaseUrl;

    @Value("${transaction.system-account-id}")
    private UUID systemAccountId;

    @Value("${interest.daily.rate:0.05}")
    private double interestRate;

    // --- Initiate ---
    public TransactionResponse initiate(TransactionRequest req) {
        if (req.getFromAccountId().equals(req.getToAccountId())) {
            throw new BadRequestException("From and To accounts must differ");
        }
        if (req.getAmount() == null || req.getAmount() <= 0) {
            throw new BadRequestException("Amount must be greater than zero");
        }

        // --- Check accounts exist ---
        try {
            AccountSummary fromAccount = restTemplate.getForObject(
                    accountServiceBaseUrl + "/accounts/" + req.getFromAccountId(),
                    AccountSummary.class
            );
            AccountSummary toAccount = restTemplate.getForObject(
                    accountServiceBaseUrl + "/accounts/" + req.getToAccountId(),
                    AccountSummary.class
            );

            if (fromAccount == null || toAccount == null) {
                throw new BadRequestException("Invalid 'from' or 'to' account ID.");
            }

            // --- Check balance ---
            if (fromAccount.getBalance() < req.getAmount()) {
                throw new BadRequestException("Insufficient funds in source account.");
            }


        } catch (HttpClientErrorException.NotFound e) {
            throw new BadRequestException("Invalid 'from' or 'to' account ID.");
        } catch (Exception e) {
            throw new BadRequestException("Failed to validate accounts: " + e.getMessage());
        }

        // --- If all checks passed, create INITIATED transaction ---
        TransactionEntity tx = mapper.toEntity(req);
        tx.setTransactionId(UUID.randomUUID().toString());
        tx.setStatus(TransactionStatus.INITIATED);
        tx.setTimestamp(LocalDateTime.now());
        repo.save(tx);

        return mapper.toResponse(tx);
    }


    // --- Execute ---
    public TransactionResponse execute(TransactionExecutionRequest req) {
        TransactionEntity tx = repo.findByTransactionId(req.getTransactionId())
                .orElseThrow(() -> new NotFoundException("Transaction not found"));

        try {
            restTemplate.put(accountServiceBaseUrl + "/accounts/transfer",
                    new AccountTransferRequest(BigDecimal.valueOf(tx.getAmount()), UUID.fromString(tx.getFromAccountId()), UUID.fromString(tx.getToAccountId())));
            tx.setStatus(TransactionStatus.SUCCESS);
        } catch (Exception e) {
            tx.setStatus(TransactionStatus.FAILED);
        }
        tx.setTimestamp(LocalDateTime.now());
        repo.save(tx);
        return mapper.toResponse(tx);
    }

    // --- History ---
    public List<TransactionEntity> getHistory(String accountId) {
        List<TransactionEntity> history = repo.findByFromAccountIdOrToAccountIdOrderByTimestampDesc(accountId, accountId);

        if (history.isEmpty()) {
            throw new NotFoundException(
                    "No transactions found for account ID " + accountId + "."
            );
        }

        return history;
    }

    // --- Daily Interest ---
    @Scheduled(cron = "0 0 0 * * ?")
    public void creditDailyInterest() {
        AccountSummary[] accounts = restTemplate.getForObject(
                accountServiceBaseUrl + "/users/all/active-savings", AccountSummary[].class);
        if (accounts == null) return;
        for (AccountSummary acc : accounts) {
            if (acc.getAccountId().equals(systemAccountId.toString())) {
                continue;
            }
            double interest = acc.getBalance() * interestRate / 365.0;
            if (interest > 0) {
                TransactionRequest req = new TransactionRequest();
                req.setFromAccountId(systemAccountId.toString());
                req.setToAccountId(acc.getAccountId());
                req.setAmount(interest);
                req.setDescription("Daily interest");
                TransactionResponse init = initiate(req);
                execute(new TransactionExecutionRequest(init.getTransactionId()));
                System.out.println("Daily interest: Crediting " + interest + " to account " + acc.getAccountId());
            }
        }
    }
}
