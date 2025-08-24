package com.project.bank.bff_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Final payload for GET /bff/dashboard/{userId}
 * We’ll populate this from UserProfileResponse, AccountDto, and TransactionDto.
 */
@Data
@NoArgsConstructor
public class DashboardResponse {
    private UUID userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private List<AccountBlock> accounts;

    @Data @NoArgsConstructor
    public static class AccountBlock {
        private String accountId;          // AccountDto uses UUID; we can accept String to be flexible
        private String accountNumber;      // optional (depends on what you expose)
        private String accountType;        // "SAVINGS", "CHECKING", ...
        private BigDecimal balance;
        private List<TxItem> transactions; // filled per account
    }

    @Data @NoArgsConstructor
    public static class TxItem {
        private String transactionId;
        private Double amount;             // matches TransactionDto
        private String toAccountId;
        private String description;
        private LocalDateTime timestamp;   // matches TransactionDto
    }
}

