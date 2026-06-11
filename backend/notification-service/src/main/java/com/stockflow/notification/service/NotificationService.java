package com.stockflow.notification.service;

import com.stockflow.notification.model.Notification;
import com.stockflow.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {
    private final NotificationRepository repo;
    public NotificationService(NotificationRepository repo) { this.repo = repo; }
    public List<Notification> getNotifications(UUID userId) {
        return repo.findByUserIdOrderByCreatedAtDesc(userId);
    }
    public List<Notification> getUnreadNotifications(UUID userId) {
        return repo.findByUserIdAndReadFalseOrderByCreatedAtDesc(userId);
    }
    public long getUnreadCount(UUID userId) {
        return repo.countByUserIdAndReadFalse(userId);
    }
    public void markAsRead(String notificationId) {
        repo.findById(notificationId).ifPresent(n -> { n.setRead(true); repo.save(n); });
    }
    public Notification createNotification(Notification n) {
        return repo.save(n);
    }
}
