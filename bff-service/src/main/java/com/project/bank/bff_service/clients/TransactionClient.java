package com.project.bank.bff_service.clients;

import com.project.bank.bff_service.dto.TransactionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "transaction-service", url = "${svc.tx.base-url}")
public interface TransactionClient {
    @GetMapping("/accounts/{accountId}/transactions")
    List<TransactionDto> getHistory(@PathVariable("accountId") String accountId);
}
