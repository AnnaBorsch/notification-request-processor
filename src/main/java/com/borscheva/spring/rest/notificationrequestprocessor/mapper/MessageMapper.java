package com.borscheva.spring.rest.notificationrequestprocessor.mapper;

import com.borscheva.spring.rest.notificationrequestprocessor.api.request.NotificationRequest;
import com.borscheva.spring.rest.notificationrequestprocessor.model.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public Message toMessage(NotificationRequest request) {
        return Message.builder()
            .type(request.getType())
            .message(request.getMessage())
            .build();
    }
}