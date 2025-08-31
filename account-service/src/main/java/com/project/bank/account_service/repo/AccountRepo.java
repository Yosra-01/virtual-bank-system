package com.project.bank.account_service.repo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.project.bank.account_service.model.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.bank.account_service.model.Account;
import com.project.bank.account_service.model.AccountStatus;

@Repository
public interface AccountRepo extends JpaRepository<Account, UUID> {

    List<Account> findByUserId(UUID userId);

    Boolean existsByAccountNumber(String accountNumber);

    //scheduled job 
    List<Account> findByStatusAndLastTransactionBefore(AccountStatus status, LocalDateTime cutoffTime);

    List<Account> findByStatusAndAccountType(AccountStatus status, AccountType accountType);

}
