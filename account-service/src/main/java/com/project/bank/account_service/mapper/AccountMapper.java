package com.project.bank.account_service.mapper;

import org.mapstruct.Mapper;

import com.project.bank.account_service.dto.AccountRequest;
import com.project.bank.account_service.model.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountRequest toDto(Account account);
    Account toEntity(AccountRequest dto);

}
