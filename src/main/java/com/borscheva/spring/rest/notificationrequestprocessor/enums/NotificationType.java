package com.borscheva.spring.rest.notificationrequestprocessor.enums;

import lombok.Getter;

@Getter
public enum NotificationType {
    
    SMS("sms-events") {
        @Override
        public String prepareMessage(String originalMessage) {
            return String.format("{\"sms\": {\"text\": \"%s\", \"sender\": \"InfoService\"}}", originalMessage);
        }
    },
    
    EMAIL("email-events") {
        @Override
        public String prepareMessage(String originalMessage) {
            return String.format("{\"email\": {\"body\": \"%s\", \"subject\": \"Notification\", \"format\": \"HTML\"}}", originalMessage);
        }
    },
    
    PUSH("push-events") {
        @Override
        public String prepareMessage(String originalMessage) {
            return String.format("{\"push\": {\"title\": \"New Notification\", \"body\": \"%s\", \"sound\": \"default\"}}", originalMessage);
        }
    },
    
    TG_MESSAGE("telegram-events") {
        @Override
        public String prepareMessage(String originalMessage) {
            return String.format("{\"telegram\": {\"text\": \"%s\", \"parse_mode\": \"HTML\", \"disable_notification\": false}}", originalMessage);
        }
    };
    
    private final String topicName;
    
    NotificationType(String topicName) {
        this.topicName = topicName;
    }

    public abstract String prepareMessage(String originalMessage);
    
    public static NotificationType fromString(String type) {
        try {
            return valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Неизвестный тип уведомления: " + type);
        }
    }
}