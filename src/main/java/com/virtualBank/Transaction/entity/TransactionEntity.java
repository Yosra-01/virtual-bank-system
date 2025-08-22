package com.virtualBank.Transaction.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String transactionId;

    private String fromAccountId;
    private String toAccountId;
    private Double amount;
    private String description;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private LocalDateTime timestamp;
}
