package com.pharmacy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pharmacy.entity.Notification;
import com.pharmacy.entity.Notification.NotificationType;
import com.pharmacy.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    
    @Autowired
    private NotificationService notificationService;
    
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Notification> createNotification(
            @RequestParam String userName,
            @RequestParam String title,
            @RequestParam String message,
            @RequestParam NotificationType type,
            @RequestParam(required = false) String referenceId,
            @RequestParam(required = false) String referenceType,
            @RequestParam(required = false) String actionUrl) {
        
        Notification notification = notificationService.createNotification(
            userName, title, message, type, referenceId, referenceType, actionUrl);
        return ResponseEntity.ok(notification);
    }
    
    @GetMapping("/user/{userName}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<Notification>> getUserNotifications(
            @PathVariable String userName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Notification> notifications = notificationService.getUserNotifications(userName, pageable);
        return ResponseEntity.ok(notifications);
    }
    
    @GetMapping("/user/{userName}/unread")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@PathVariable String userName) {
        List<Notification> notifications = notificationService.getUnreadNotifications(userName);
        return ResponseEntity.ok(notifications);
    }
    
    @GetMapping("/user/{userName}/unread-count")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Long> getUnreadNotificationCount(@PathVariable String userName) {
        Long count = notificationService.getUnreadNotificationCount(userName);
        return ResponseEntity.ok(count);
    }
    
    @PutMapping("/{notificationId}/mark-read")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long notificationId) {
        Notification notification = notificationService.markAsRead(notificationId);
        return ResponseEntity.ok(notification);
    }
    
    @PutMapping("/user/{userName}/mark-all-read")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> markAllAsRead(@PathVariable String userName) {
        notificationService.markAllAsRead(userName);
        return ResponseEntity.ok("All notifications marked as read");
    }
    
    @DeleteMapping("/{notificationId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok("Notification deleted successfully");
    }
}