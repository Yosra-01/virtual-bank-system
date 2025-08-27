package com.project.bank.Transaction.dto;
 
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class LogMessage { 
     private String message;
    private String messageType; //"Request" or "Response"
    private String timestamp;
    private String serviceName;

    //constructors

    public LogMessage() {}

    public LogMessage(String message, String messageType, String timestamp, String serviceName) {
        this.message = message;
        this.messageType = messageType;
        this.timestamp = timestamp;
        this.serviceName = serviceName;
    }
    
 
    //getters & setters

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
