package com.project.bank.account_service.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bank.account_service.dto.LogMessage;



@Service
public class LoggingProducerService {

    private final KafkaTemplate<String, LogMessage> kafkaTemplate;
    private final ObjectMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(LoggingProducerService.class);

    @Value("${spring.application.name}") 
    private String serviceName;

    public LoggingProducerService(KafkaTemplate<String, LogMessage> kafkaTemplate, ObjectMapper mapper){
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
    }

    public <T> void sendLog(T message, String messageType){

        try {
            String json = mapper.writeValueAsString(message);
            LogMessage logMessage = new LogMessage();
            
            logMessage.setMessage(json);
            logMessage.setMessageType(messageType);
            logMessage.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
            logMessage.setServiceName(serviceName);

            kafkaTemplate.send("bankservices", logMessage);
            logger.info("Serialization successful, sending to Kafka");
            
        } 
        catch (JsonProcessingException e) {
                    
            LogMessage logMessage = new LogMessage();
            
            logMessage.setMessage("failed to serialize" + e.getMessage());
            logMessage.setMessageType(messageType);
            logMessage.setTimestamp(LocalDateTime.now().toString());
            logMessage.setServiceName(serviceName);

            kafkaTemplate.send("bankservices", logMessage);
            logger.error("Failed to serialize payload", e);

        }
        

    }

}
