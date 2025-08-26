package com.project.bank.account_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.project.bank.account_service.service.LoggingProducerService;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final LoggingProducerService loggingProducer;

    public GlobalExceptionHandler(LoggingProducerService loggingProducer){
        this.loggingProducer = loggingProducer;
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e){

        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode().getCode(), e.getErrorCode().getMessage());

        loggingProducer.sendLog(errorResponse, "Response");
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(errorResponse);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e) {
        
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INVALID_INPUT.getCode(), ErrorCode.INVALID_INPUT.getMessage());

        loggingProducer.sendLog(errorResponse, "Response");
        return ResponseEntity.status(ErrorCode.INVALID_INPUT.getStatus()).body(errorResponse);
    }


}

