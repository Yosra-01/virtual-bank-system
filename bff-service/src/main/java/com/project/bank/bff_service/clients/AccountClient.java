
package com.project.bank.bff_service.clients;

import com.project.bank.bff_service.dto.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "account-service", url = "${svc.account.base-url}")
public interface AccountClient {

    @GetMapping("/users/{userId}/accounts")
    List<AccountDto> getAccountsByUser(@PathVariable("userId") UUID userId);

}
