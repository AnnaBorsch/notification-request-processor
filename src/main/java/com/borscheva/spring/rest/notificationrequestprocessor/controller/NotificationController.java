package com.borscheva.spring.rest.notificationrequestprocessor.controller;

import com.borscheva.spring.rest.notificationrequestprocessor.api.request.NotificationRequest;
import com.borscheva.spring.rest.notificationrequestprocessor.dto.NotificationResponse;
import com.borscheva.spring.rest.notificationrequestprocessor.model.Message;
import com.borscheva.spring.rest.notificationrequestprocessor.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
@Slf4j
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(@RequestBody @Valid NotificationRequest request) {
        log.info("Получен запрос: type={}, message={}", request.getType(), request.getMessage());
        
        Message message = notificationService.saveNotification(request);
        
        NotificationResponse response = new NotificationResponse(
            message.getId(),
            message.getType(),
            message.getMessage(),
            "ACCEPTED"
        );
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }
}