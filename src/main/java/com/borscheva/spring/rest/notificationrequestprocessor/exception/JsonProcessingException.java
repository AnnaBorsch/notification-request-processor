package com.borscheva.spring.rest.notificationrequestprocessor.exception;

public class JsonProcessingException extends RuntimeException {
    public JsonProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}