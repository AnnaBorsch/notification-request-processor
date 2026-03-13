package com.borscheva.spring.rest.notificationrequestprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NotificationRequestProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationRequestProcessorApplication.class, args);
    }

}
