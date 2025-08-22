package com.project.bank.account_service.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bank.account_service.model.Account;

public interface AccountRepo extends JpaRepository<Account, UUID> {

    List<Account> findByUserId(UUID userId);
    Boolean existsByAccountNumber(String accountNumber);
}
