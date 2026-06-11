package com.stockflow.notification.repository;

import com.stockflow.notification.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByUserIdOrderByCreatedAtDesc(UUID userId);
    List<Notification> findByUserIdAndReadFalseOrderByCreatedAtDesc(UUID userId);
    long countByUserIdAndReadFalse(UUID userId);
}
