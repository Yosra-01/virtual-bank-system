package com.project.bank.account_service.mapper;

import org.mapstruct.Mapper;

import com.project.bank.account_service.dto.AccountDto;
import com.project.bank.account_service.model.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDto toDto(Account account);
    Account toEntity(AccountDto dto);

}
