package com.project.bank.account_service.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.project.bank.account_service.dto.AccountRequest;
import com.project.bank.account_service.dto.AccountResponse;
import com.project.bank.account_service.exception.BusinessException;
import com.project.bank.account_service.exception.ErrorCode;
import com.project.bank.account_service.mapper.AccountMapper;
import com.project.bank.account_service.model.Account;
import com.project.bank.account_service.model.AccountStatus;
//import com.project.bank.account_service.model.AccountType;
import com.project.bank.account_service.repo.AccountRepo;
//import com.project.bank.account_service.utils.AccountNumberGenerator;
import com.project.bank.account_service.utils.AccountNumberGenerator;

@Service
public class AccountService {

    private final AccountRepo accountRepo;
    private final AccountMapper accountMapper;
    
    public AccountService(AccountRepo accountRepo, AccountMapper accountMapper ) {
        this.accountRepo = accountRepo;
        this.accountMapper = accountMapper;
    }
    

    // -> post
    public AccountResponse createAccount(AccountRequest accountRequest){

        Account account = accountMapper.toEntity(accountRequest);

        //if(accountRequest.getBalance().equals(null))
           // throw new BusinessException(ErrorCode.INVALID_INPUT);

        account.setAccountNumber(AccountNumberGenerator.generate(accountRepo));
        account.setStatus(AccountStatus.ACTIVE);
        account.setLastTransaction(null);

        Account newAccount = accountRepo.save(account); 

        AccountResponse response = accountMapper.toResponse(newAccount);
        return response;
    }

    // -> get
    public AccountResponse getAccount(UUID accountId){
        Account account =  accountRepo.findById(accountId).orElseThrow(() -> new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND));
        AccountResponse response = accountMapper.toResponse(account);
        return response;
    }

    public List<AccountResponse> getUserAccounts(UUID userId){
        List<Account> accounts = accountRepo.findByUserId(userId);

        if(accounts.isEmpty())
            throw new BusinessException(ErrorCode.ACCOUNTS_NOT_FOUND);

        List<AccountResponse> response = accountMapper.toResponseList(accounts);
        return response;
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

        //keep track for scheduled job
        sender.setLastTransaction(LocalDateTime.now());
        receiver.setLastTransaction(LocalDateTime.now());

        return TransferStatus.SUCCESS;

    }

    //scheduled job service logic
    public void deactivateStaleAccounts(){

        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(24);
        List<Account> staleAccounts = accountRepo.findByStatusAndLastTransactionBefore(AccountStatus.ACTIVE, cutoffTime);

        for (Account account : staleAccounts) {
            account.setStatus(AccountStatus.INACTIVE);
        } 

        accountRepo.saveAll(staleAccounts);

    }
    
    @Scheduled(fixedRate = 3_600_000) //1 hour in msec
    public void scheduledJob(){
        deactivateStaleAccounts();
    }

}
