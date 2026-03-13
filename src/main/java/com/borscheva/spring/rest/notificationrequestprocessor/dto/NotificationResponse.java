package com.borscheva.spring.rest.notificationrequestprocessor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class NotificationResponse {
    private UUID id;
    private String type;
    private String message;
    private String status;
}