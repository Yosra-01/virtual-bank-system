package com.project.bank.Transaction.service;

import com.project.bank.Transaction.dto.LogMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
    
import java.time.LocalDateTime;

@Service
public class LoggingProducer {

    private final KafkaTemplate<String, LogMessage> kafkaTemplate;

    @Value("${spring.application.name}") 
    private String serviceName;

    public LoggingProducer(KafkaTemplate<String, LogMessage> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public <T> void sendLog(T message, String messageType){
        
        LogMessage logMessage = new LogMessage();
        
        logMessage.setMessage(message);
        logMessage.setMessageType(messageType);
        logMessage.setTimestamp(LocalDateTime.now());
        logMessage.setServiceName(serviceName);

        kafkaTemplate.send("bankservices", logMessage);
    }
}
