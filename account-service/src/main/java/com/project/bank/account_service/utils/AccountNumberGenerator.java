package com.project.bank.account_service.utils;

import java.security.SecureRandom;

import com.project.bank.account_service.repo.AccountRepo;

public class AccountNumberGenerator {

    private static final SecureRandom random = new SecureRandom();

    public static String generate(AccountRepo accountRepo){
        
        String accountNumber;

        do{
            long generatedNumber = Math.abs(random.nextLong())%10_000_000_000L;
            accountNumber = String.format("%010d", generatedNumber);

        }while(accountRepo.existsByAccountNumber(accountNumber)); //keep generating until unique

        return accountNumber;
    }

}
