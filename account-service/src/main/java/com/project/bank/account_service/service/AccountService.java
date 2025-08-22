package com.project.bank.account_service.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.project.bank.account_service.model.Account;
import com.project.bank.account_service.model.AccountType;
import com.project.bank.account_service.repo.AccountRepo;
import com.project.bank.account_service.utils.AccountNumberGenerator;

public class AccountService {

    private final AccountRepo accountRepo;

    public AccountService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public Account createAccount(UUID userId, AccountType type, BigDecimal initialbalance){
        Account account = Account.builder()
            .accountNumber(AccountNumberGenerator.generate(accountRepo))
            .accountType(type)
            .balance(initialbalance)
            .status("ACTIVE")
            .userID(userId)
            .build();
        return accountRepo.save(account);
    }

    public Optional<Account> getAccount(UUID accountId){
        return accountRepo.findById(accountId);
    }

    public List<Account> getUserAccounts(UUID userId){
        return accountRepo.findByUserId(userId);
    }

    public void transfer(BigDecimal amount, UUID senderAccount, UUID recvAccount){
        
        Account sender = accountRepo.findById(senderAccount).orElseThrow(() -> new RuntimeException("Sender Account Not Found"));
        Account reciever = accountRepo.findById(recvAccount).orElseThrow(() -> new RuntimeException("Recieving Account Not Found"));

        //sender balance < amount 
        if (sender.getBalance().compareTo(amount) < 0)
            throw new RuntimeException("Insufficient Funds");

        sender.setBalance(sender.getBalance().subtract(amount));
        reciever.setBalance(reciever.getBalance().add(amount));

        accountRepo.save(sender);
        accountRepo.save(reciever);
    }
}
