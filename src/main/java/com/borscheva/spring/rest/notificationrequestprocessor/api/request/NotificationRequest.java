package com.borscheva.spring.rest.notificationrequestprocessor.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    @NotBlank(message = "Тип уведомления не может быть пустым")
    @Pattern(regexp = "SMS|EMAIL|PUSH|TG_MESSAGE",
            message = "Тип должен быть одним из: SMS, EMAIL, PUSH, TG_MESSAGE")
    private String type;

    @NotBlank(message = "Сообщение не может быть пустым")
    private String message;
}