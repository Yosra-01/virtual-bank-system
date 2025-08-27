package com.project.bank.logging_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bank.logging_service.dto.LogMessage;
import com.project.bank.logging_service.entity.LogDumpEntity;
import com.project.bank.logging_service.repository.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LogConsumer {

    private static final Logger logger = LoggerFactory.getLogger(LogConsumer.class);
    private final LogRepository logRepository;


    public LogConsumer(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @KafkaListener(topics = "bankservices", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(LogMessage logMessage) {
        logger.info("Consumed log: {}", logMessage);

        LogDumpEntity entity = new LogDumpEntity();
        entity.setMessage(logMessage.getMessage()); // already a JSON string
        entity.setMessageType(logMessage.getMessageType());
        entity.setServiceName(logMessage.getServiceName());
        entity.setTimestamp(logMessage.getTimestamp());

        logRepository.save(entity);
    }


}
