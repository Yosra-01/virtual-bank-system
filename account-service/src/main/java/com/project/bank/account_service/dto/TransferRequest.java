package com.project.bank.account_service.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class TransferRequest {
    private BigDecimal amount;
    private UUID sender;
    private UUID receiver;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UUID getSender() {
        return sender;
    }

    public void setSender(UUID sender) {
        this.sender = sender;
    }

    public UUID getReceiver() {
        return receiver;
    }

    public void setReceiver(UUID receiver) {
        this.receiver = receiver;
    }
}
