package com.project.bank.account_service.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class TransferRequest {
    private BigDecimal amount;
    private UUID sender;
    private UUID receiver;
}
