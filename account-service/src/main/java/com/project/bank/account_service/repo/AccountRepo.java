package com.project.bank.account_service.repo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.project.bank.account_service.model.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.bank.account_service.model.Account;
import com.project.bank.account_service.model.AccountStatus;

@Repository
public interface AccountRepo extends JpaRepository<Account, UUID> {

    List<Account> findByUserId(UUID userId);

    Boolean existsByAccountNumber(String accountNumber);

    //scheduled job 
    //List<Account> findByStatusAndLastTransactionBefore(AccountStatus status, LocalDateTime cutoffTime);

    @Query("SELECT a FROM Account a WHERE a.status = :status AND (a.lastTransaction IS NULL OR a.lastTransaction < :cutoffTime)")
    List<Account> findStaleAccounts(@Param("status") AccountStatus status, @Param("cutoffTime") LocalDateTime cutoffTime);


    List<Account> findByStatusAndAccountType(AccountStatus status, AccountType accountType);

}
