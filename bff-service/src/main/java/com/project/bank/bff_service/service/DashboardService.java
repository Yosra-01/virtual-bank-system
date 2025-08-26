package com.project.bank.bff_service.service;

import com.project.bank.bff_service.clients.AccountClient;
import com.project.bank.bff_service.clients.UserClient;
import com.project.bank.bff_service.dto.*;
import com.project.bank.bff_service.dto.DashboardResponse.AccountBlock;
import com.project.bank.bff_service.dto.DashboardResponse.TxItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserClient userClient;       // Feign
    private final AccountClient accountClient; // Feign
    private final WebClient txWebClient;       // WebClient bean named txWebClient

    public DashboardResponse buildDashboard(UUID userId) {
        // 1) user profile
        UserDto profile = userClient.getUserProfile(userId, userId.toString());

        // 2) accounts for the user
        List<AccountDto> accounts = accountClient.getAccountsByUser(userId);
        if (accounts == null) accounts = List.of();

        // 3) map accounts first (without transactions)
        List<AccountBlock> blocks = new ArrayList<>(accounts.size());
        for (AccountDto acc : accounts) {
            blocks.add(toAccountBlock(acc));
        }

        // 4) async fan-out: fetch transactions for all accounts concurrently
        List<AccountBlock> blocksWithTx =
                Flux.fromIterable(blocks)
                        .flatMap(block -> {
                            String accountId = block.getAccountId();
                            if (accountId == null) {
                                block.setTransactions(List.of());
                                return Flux.just(block);
                            }
                            // GET /transactions/accounts/{accountId}/transactions (full path via txWebClient baseUrl)
                            return txWebClient.get()
                                    .uri("/transactions/accounts/{accountId}/transactions", accountId)
                                    .retrieve()
                                    // If the tx service returns 404 for “no transactions”, turn it into empty list:
                                    .onStatus(s -> s.value() == 404, resp -> {
                                        block.setTransactions(List.of());
                                        return resp.releaseBody().then(Mono.error(new RuntimeException("404-no-body")));
                                    })
                                    .bodyToFlux(TransactionDto.class)
                                    .map(this::toTxItem)
                                    .collectList()
                                    .onErrorReturn(List.of()) // swallow errors per-account
                                    .map(txItems -> {
                                        block.setTransactions(txItems);
                                        return block;
                                    })
                                    .flux();
                        })
                        .collectList()
                        .block();

        // 5) assemble final response
        DashboardResponse out = new DashboardResponse();
        out.setUserId(profile.userId);
        out.setUsername(profile.username);
        out.setEmail(profile.email);
        out.setFirstName(profile.firstName);
        out.setLastName(profile.lastName);
        out.setAccounts(blocksWithTx != null ? blocksWithTx : blocks);
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

    private TxItem toTxItem(TransactionDto t) {
        TxItem x = new TxItem();
        x.setTransactionId(t.getTransactionId());
        x.setAmount(t.getAmount());
        x.setToAccountId(t.getToAccountId());
        x.setDescription(t.getDescription());
        x.setTimestamp(t.getTimestamp());
        return x;
    }
}
