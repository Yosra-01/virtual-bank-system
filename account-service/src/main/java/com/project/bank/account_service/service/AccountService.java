package com.project.bank.account_service.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.project.bank.account_service.model.Account;
import com.project.bank.account_service.model.AccountType;
import com.project.bank.account_service.repo.AccountRepo;
import com.project.bank.account_service.utils.AccountNumberGenerator;

@Service
public class AccountService {

    private final AccountRepo accountRepo;

    public AccountService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    // -> post
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

    // -> get
    public Optional<Account> getAccount(UUID accountId){
        return accountRepo.findById(accountId);
    }

    public List<Account> getUserAccounts(UUID userId){
        return accountRepo.findByUserId(userId);
    }

    // -> put
    public TransferStatus transfer(BigDecimal amount, UUID senderAccount, UUID recvAccount){
        
        Optional<Account> _sender = accountRepo.findById(senderAccount);
        if(_sender.isEmpty()) 
            return TransferStatus.SENDER_NOT_FOUND;

        Optional<Account> _receiver = accountRepo.findById(recvAccount);
        if(_receiver.isEmpty()) 
            return TransferStatus.RECEIVER_NOT_FOUND;
        
        Account sender = _sender.get();
        Account receiver = _receiver.get();

        if (sender.getBalance().compareTo(amount) < 0)
            return TransferStatus.INSUFFICIENT_FUNDS;

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        accountRepo.save(sender);
        accountRepo.save(receiver);

        return TransferStatus.SUCCESS;

    }
}
