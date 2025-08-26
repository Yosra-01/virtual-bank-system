package com.project.bank.Transaction.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bank.Transaction.dto.LogMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
    
import java.time.LocalDateTime;

@Service
public class LoggingProducer {
    private static final Logger logger = LoggerFactory.getLogger(LoggingProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public LoggingProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendLog(String content, String type) {
        try {
            LogMessage log = new LogMessage(content, type, LocalDateTime.now().toString());
            String json = mapper.writeValueAsString(log);
            kafkaTemplate.send("logging-topic", json);
            logger.info("Sent log to Kafka: {}", json);
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize log message", e);
        }
    }
}
