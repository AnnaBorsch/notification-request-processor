package com.borscheva.spring.rest.notificationrequestprocessor.service;

import com.borscheva.spring.rest.notificationrequestprocessor.model.OutboxMessage;
import com.borscheva.spring.rest.notificationrequestprocessor.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaSender {

    private final NotificationService notificationService;
    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Transactional
    public void publish(UUID id) {

        OutboxMessage outboxMessage = notificationService.findById(id);

        try {
            kafkaTemplate.send(
                outboxMessage.getTopic(),
                outboxMessage.getMessageKey(),
                outboxMessage.getValue()
            ).get(5, TimeUnit.SECONDS);

            outboxMessage.setSent(true);
            outboxRepository.save(outboxMessage);

            log.info("Сообщение отправлено: {}, попытка={}",
                outboxMessage.getMessageKey(),
                outboxMessage.getAttemptCount());
                
        } catch (Exception e) {
            notificationService.incrementAttempt(outboxMessage.getId());
            log.warn("Неудачная попытка отправки сообщения: {}, attempt={}",
                outboxMessage.getMessageKey(),
                outboxMessage.getAttemptCount() + 1,
                e);
        }
    }
}