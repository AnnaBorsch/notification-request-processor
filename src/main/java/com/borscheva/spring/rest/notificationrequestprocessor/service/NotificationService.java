package com.borscheva.spring.rest.notificationrequestprocessor.service;

import com.borscheva.spring.rest.notificationrequestprocessor.api.request.NotificationRequest;
import com.borscheva.spring.rest.notificationrequestprocessor.dto.MessageDto;
import com.borscheva.spring.rest.notificationrequestprocessor.enums.NotificationType;
import com.borscheva.spring.rest.notificationrequestprocessor.exception.JsonProcessingException;
import com.borscheva.spring.rest.notificationrequestprocessor.mapper.MessageMapper;
import com.borscheva.spring.rest.notificationrequestprocessor.mapper.OutboxMapper;
import com.borscheva.spring.rest.notificationrequestprocessor.model.Message;
import com.borscheva.spring.rest.notificationrequestprocessor.model.OutboxMessage;
import com.borscheva.spring.rest.notificationrequestprocessor.repository.MessageRepository;
import com.borscheva.spring.rest.notificationrequestprocessor.repository.OutboxRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final OutboxRepository outboxRepository;
    private final MessageRepository messageRepository;
    private final ObjectMapper objectMapper;
    private final OutboxMapper outboxMapper;
    private final MessageMapper messageMapper;

    @Transactional
    public Message saveNotification(NotificationRequest request) {
        try {
            NotificationType type = NotificationType.fromString(request.getType());

            Message message = messageMapper.toMessage(request);
            messageRepository.save(message);

            String preparedMessage = type.prepareMessage(request.getMessage());
            MessageDto messageDto = new MessageDto(preparedMessage);
            String jsonPayload = objectMapper.writeValueAsString(messageDto);

            OutboxMessage outboxMessage = outboxMapper.toOutboxMessage(type, jsonPayload);
            outboxRepository.save(outboxMessage);

            log.info("Подготовлено сообщение для отправки. Key: {}, Payload: {}, topic: {}",
                    outboxMessage.getMessageKey(),
                    outboxMessage.getValue(),
                    outboxMessage.getTopic());

            return message;

        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new JsonProcessingException("Ошибка при сериализации сообщения", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void incrementAttempt(UUID id) {
        outboxRepository.incrementAttempt(id);
    }

    public OutboxMessage findById(UUID id) {
        return outboxRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Outbox message not found: " + id));
    }
}