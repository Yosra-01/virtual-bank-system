package com.project.bank.user_service.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.project.bank.user_service.DTOs.LogMessage; 

@Configuration
public class KafkaProducerConfig {

   @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    //producer properties
    public Map<String, Object> producerConfig(){
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return properties; 
    }

    //allows creation of kafka producers 
    @Bean
    public ProducerFactory<String, LogMessage> producerFactory(){ 
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    //high level api for sending messages to kafka topics --not needed..?
    @Bean
    public KafkaTemplate<String, LogMessage> kafkaTemplate(ProducerFactory<String,LogMessage> producerFactory){
        return new KafkaTemplate<>(producerFactory);
    }

}
