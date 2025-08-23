package com.project.bank.Transaction.repository;

import com.project.bank.Transaction.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    Optional<TransactionEntity> findByTransactionId(String transactionId);
    List<TransactionEntity> findByFromAccountIdOrToAccountIdOrderByTimestampDesc(String from, String to);
}
