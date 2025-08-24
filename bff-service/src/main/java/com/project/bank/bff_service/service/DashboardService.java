package com.project.bank.bff_service.service;

import com.project.bank.bff_service.clients.AccountClient;
import com.project.bank.bff_service.clients.TransactionClient;
import com.project.bank.bff_service.clients.UserClient;
import com.project.bank.bff_service.dto.*;
import com.project.bank.bff_service.dto.DashboardResponse.AccountBlock;
import com.project.bank.bff_service.dto.DashboardResponse.TxItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserClient userClient;
    private final AccountClient accountClient;
    private final TransactionClient txClient;

    public DashboardResponse buildDashboard(UUID userId) {
        // 1) user profile (your user-service requires X-User-Id header equal to the path id)
        UserDto profile = userClient.getUserProfile(userId, userId.toString());

        // 2) accounts for the user
        List<AccountDto> accounts = accountClient.getAccountsByUser(userId);
        if (accounts == null) accounts = List.of();

        // 3) for each account, fetch transactions
        List<AccountBlock> blocks = new ArrayList<>(accounts.size());
        for (AccountDto acc : accounts) {
            AccountBlock block = toAccountBlock(acc);

            // TransactionClient expects String accountId (per your controller/entity)
            String accIdStr = acc.getAccountId() != null ? acc.getAccountId().toString() : null;

            List<TransactionDto> txList = (accIdStr != null)
                    ? txClient.getHistory(accIdStr)
                    : List.of();
            if (txList == null) txList = List.of();

            block.setTransactions(toTxItems(txList));
            blocks.add(block);
        }

        // 4) assemble final response
        DashboardResponse out = new DashboardResponse();
        out.setUserId(profile.userId);
        out.setUsername(profile.username);
        out.setEmail(profile.email);
        out.setFirstName(profile.firstName);
        out.setLastName(profile.lastName);
        out.setAccounts(blocks);
        return out;
    }

    /* ---------- mapping helpers ---------- */

    private AccountBlock toAccountBlock(AccountDto a) {
        AccountBlock b = new AccountBlock();
        b.setAccountId(a.getAccountId() != null ? a.getAccountId().toString() : null);
        b.setAccountNumber(a.getAccountNumber());
        b.setAccountType(a.getAccountType());
        b.setBalance(a.getBalance());
        return b;
    }

    private List<TxItem> toTxItems(List<TransactionDto> txs) {
        List<TxItem> out = new ArrayList<>(txs.size());
        for (TransactionDto t : txs) {
            TxItem x = new TxItem();
            x.setTransactionId(t.getTransactionId());
            x.setAmount(t.getAmount());
            x.setToAccountId(t.getToAccountId());
            x.setDescription(t.getDescription());
            x.setTimestamp(t.getTimestamp()); // LocalDateTime as in your TransactionEntity
            out.add(x);
        }
        return out;
    }
}
