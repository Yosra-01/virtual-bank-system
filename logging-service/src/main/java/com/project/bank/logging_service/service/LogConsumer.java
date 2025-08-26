package com.project.bank.logging_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bank.logging_service.dto.LogMessage;
import com.project.bank.logging_service.entity.LogDumpEntity;
import com.project.bank.logging_service.repository.LogRepository;

@Service
public class LogConsumer {

    private final LogRepository logRepository;
    private final ObjectMapper objectMapper;

    public LogConsumer(LogRepository logRepository, ObjectMapper objectMapper) {
        this.logRepository = logRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "bankservices", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(LogMessage logMessage) {
        try {
            // convert message object to JSON string for storage
            String jsonMessage = objectMapper.writeValueAsString(logMessage.getMessage());

            LogDumpEntity logEntity = new LogDumpEntity();
            logEntity.setMessage(jsonMessage);
            logEntity.setMessageType(logMessage.getMessageType());
            logEntity.setTimestamp(logMessage.getTimestamp());
            logEntity.setServiceName(logMessage.getServiceName());

            logRepository.save(logEntity);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
