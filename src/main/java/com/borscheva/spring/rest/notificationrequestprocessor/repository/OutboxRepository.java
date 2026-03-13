package com.borscheva.spring.rest.notificationrequestprocessor.repository;

import com.borscheva.spring.rest.notificationrequestprocessor.model.OutboxMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OutboxRepository extends JpaRepository<OutboxMessage, UUID> {

    @Query("""
        SELECT o FROM OutboxMessage o
        WHERE o.sent = false
        ORDER BY o.createdAt ASC
        """)
    List<OutboxMessage> findBatch(Pageable pageable);

    @Modifying
    @Query("""
        UPDATE OutboxMessage o
        SET o.attemptCount = o.attemptCount + 1
        WHERE o.id = :id
        """)
    void incrementAttempt(@Param("id") UUID id);
}