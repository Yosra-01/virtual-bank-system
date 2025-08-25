package com.project.bank.account_service.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "ANF_404", "Account not found"),
    ACCOUNTS_NOT_FOUND(HttpStatus.NOT_FOUND, "UANF_404", "No Accounts found"),
    SENDER_NOT_FOUND(HttpStatus.NOT_FOUND, "SND_404", "Sender Account not found"),
    RECEIVER_NOT_FOUND(HttpStatus.NOT_FOUND, "RCV_404", "Recieving Account not found"),
    INSUFFICIENT_FUNDS( HttpStatus.BAD_REQUEST, "FUND_400", "Transfer failed due to insufficient funds"),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "INV_400", "Invalid input data");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
    
    public HttpStatus getStatus(){return status;}
    public String getCode(){return code;}
    public String getMessage(){return message;}

}
