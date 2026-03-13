package com.borscheva.spring.rest.notificationrequestprocessor.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.outbox")
@Getter
@Setter
public class OutboxProperties {

    private int batchSize;
    private long delayMs;
}