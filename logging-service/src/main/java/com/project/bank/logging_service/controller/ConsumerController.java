package com.project.bank.logging_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.bank.logging_service.entity.LogDumpEntity;
import com.project.bank.logging_service.repository.LogRepository;

@RestController
public class ConsumerController {

    private final LogRepository logRepository;

    public ConsumerController(LogRepository logRepository) {
        this.logRepository = logRepository;
    }


    @GetMapping("/logs")
    public List<LogDumpEntity> getAllLogs() {
        return logRepository.findAll();
    }


}
