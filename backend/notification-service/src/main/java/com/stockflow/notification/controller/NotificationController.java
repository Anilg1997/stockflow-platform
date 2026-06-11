package com.stockflow.notification.controller;

import com.stockflow.common.dto.ApiResponse;
import com.stockflow.notification.model.Notification;
import com.stockflow.notification.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService service;
    public NotificationController(NotificationService service) { this.service = service; }
    @GetMapping
    public ResponseEntity<ApiResponse<List<Notification>>> getNotifications(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(ApiResponse.ok(service.getNotifications(userId)));
    }
    @GetMapping("/unread")
    public ResponseEntity<ApiResponse<List<Notification>>> getUnread(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(ApiResponse.ok(service.getUnreadNotifications(userId)));
    }
    @GetMapping("/unread/count")
    public ResponseEntity<ApiResponse<Long>> getUnreadCount(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(ApiResponse.ok(service.getUnreadCount(userId)));
    }
    @PostMapping("/{id}/read")
    public ResponseEntity<ApiResponse<Void>> markAsRead(@PathVariable String id) {
        service.markAsRead(id);
        return ResponseEntity.ok(ApiResponse.ok("Marked as read", null));
    }
}
