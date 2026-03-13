package com.borscheva.spring.rest.notificationrequestprocessor.service;

import com.borscheva.spring.rest.notificationrequestprocessor.config.OutboxProperties;
import com.borscheva.spring.rest.notificationrequestprocessor.model.OutboxMessage;
import com.borscheva.spring.rest.notificationrequestprocessor.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutboxScheduler {

    private final OutboxRepository outboxRepository;
    private final OutboxProperties properties;
    private final KafkaSender kafkaSender;

    @Scheduled(fixedDelayString = "#{@outboxProperties.delayMs}")
    public void publishOutbox() {

        Pageable limit = PageRequest.of(0, properties.getBatchSize());
        List<OutboxMessage> messages = outboxRepository.findBatch(limit);

        if (messages.isEmpty()) {
            log.debug("Нет сообщений для отправки");
            return;
        }

        log.info("Найдено {} сообщений для отправки", messages.size());

        for (OutboxMessage message : messages) {
            kafkaSender.publish(message.getId());
        }
    }
}