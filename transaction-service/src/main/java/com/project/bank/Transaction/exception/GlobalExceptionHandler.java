package com.project.bank.Transaction.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.bank.Transaction.service.LoggingProducer;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    
    private final LoggingProducer loggingProducer;

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex) {
        loggingProducer.sendLog(ex.getMessage(), "Response");
        return ResponseEntity.status(404).body(new ApiError(404,"Not Found", ex.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadReq(BadRequestException ex) {
        loggingProducer.sendLog(ex.getMessage(), "Response");
        return ResponseEntity.status(400).body(new ApiError(400,"Bad Request", ex.getMessage()));
    }
}
