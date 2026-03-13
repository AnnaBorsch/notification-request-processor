package com.borscheva.spring.rest.notificationrequestprocessor.repository;

import com.borscheva.spring.rest.notificationrequestprocessor.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
}