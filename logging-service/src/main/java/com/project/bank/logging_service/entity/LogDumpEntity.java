package com.project.bank.logging_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;

@Entity
@Table(name = "Logs")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class LogDumpEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "LONGTEXT")
    private String message;

    private String messageType;
    private String timestamp;
    private String serviceName;

    //constructors
    public LogDumpEntity() {}
    public LogDumpEntity(Long id, String message, String messageType, String timestamp, String serviceName) {
        this.id = id;
        this.message = message;
        this.messageType = messageType;
        this.timestamp = timestamp;
        this.serviceName = serviceName;
    }
    
    //getters & setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessageType() {
        return messageType;
    }
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    


}
