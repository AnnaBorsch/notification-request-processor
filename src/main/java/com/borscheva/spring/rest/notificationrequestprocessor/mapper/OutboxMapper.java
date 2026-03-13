package com.borscheva.spring.rest.notificationrequestprocessor.mapper;

import com.borscheva.spring.rest.notificationrequestprocessor.enums.NotificationType;
import com.borscheva.spring.rest.notificationrequestprocessor.model.OutboxMessage;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OutboxMapper {

    public OutboxMessage toOutboxMessage(NotificationType type, String jsonPayload) {
        return OutboxMessage.builder()
            .messageKey(UUID.randomUUID().toString())
            .topic(type.getTopicName())
            .value(jsonPayload)
            .sent(false)
            .attemptCount(0)
            .build();
    }
}