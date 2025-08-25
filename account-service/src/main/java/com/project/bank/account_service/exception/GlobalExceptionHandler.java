package com.project.bank.account_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e){

        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode().getCode(), e.getErrorCode().getMessage());
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(errorResponse);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e) {
        
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INVALID_INPUT.getCode(), ErrorCode.INVALID_INPUT.getMessage());
        return ResponseEntity.status(ErrorCode.INVALID_INPUT.getStatus()).body(errorResponse);
    }


}

