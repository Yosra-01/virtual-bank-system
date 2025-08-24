package com.project.bank.account_service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.bank.account_service.dto.AccountRequest;
import com.project.bank.account_service.dto.AccountResponse;
import com.project.bank.account_service.model.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountRequest toRequest(Account account);
    Account toEntity(AccountRequest request);

    AccountResponse toResponse(Account account);
    Account toEntity(AccountResponse response);

    List<AccountResponse> toResponseList(List<Account> accountList);

}
