package com.pharmacy.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.user.entity.UserProfiles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifications")
public class Notification {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_name", referencedColumnName = "userName")
    private UserProfiles userProfile;
    
    @Column(name = "title", nullable = false, length = 200)
    private String title;
    
    @Column(name = "message", nullable = false, length = 1000)
    private String message;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NotificationType type;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private NotificationStatus status = NotificationStatus.UNREAD;
    
    @Column(name = "reference_id")
    private String referenceId; // Order ID, Medicine ID, etc.
    
    @Column(name = "reference_type")
    private String referenceType; // ORDER, MEDICINE, PRESCRIPTION, etc.
    
    @Column(name = "action_url")
    private String actionUrl;
    
    @Column(name = "is_read")
    private Boolean isRead = false;
    
    @Column(name = "read_at")
    private LocalDateTime readAt;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    public enum NotificationType {
        ORDER_CONFIRMATION, ORDER_SHIPPED, ORDER_DELIVERED, ORDER_CANCELLED,
        PRESCRIPTION_UPLOADED, PRESCRIPTION_VERIFIED, PRESCRIPTION_REJECTED,
        MEDICINE_OUT_OF_STOCK, MEDICINE_EXPIRY_ALERT, PAYMENT_SUCCESS, PAYMENT_FAILED,
        PROMOTIONAL, REMINDER, SYSTEM_ALERT
    }
    
    public enum NotificationStatus {
        UNREAD, READ, ARCHIVED
    }
    
    // Constructors
    public Notification() {}
    
    public Notification(UserProfiles userProfile, String title, String message, NotificationType type) {
        this.userProfile = userProfile;
        this.title = title;
        this.message = message;
        this.type = type;
    }
    
    // Helper methods
    public void markAsRead() {
        this.isRead = true;
        this.status = NotificationStatus.READ;
        this.readAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public UserProfiles getUserProfile() { return userProfile; }
    public void setUserProfile(UserProfiles userProfile) { this.userProfile = userProfile; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public NotificationType getType() { return type; }
    public void setType(NotificationType type) { this.type = type; }
    
    public NotificationStatus getStatus() { return status; }
    public void setStatus(NotificationStatus status) { this.status = status; }
    
    public String getReferenceId() { return referenceId; }
    public void setReferenceId(String referenceId) { this.referenceId = referenceId; }
    
    public String getReferenceType() { return referenceType; }
    public void setReferenceType(String referenceType) { this.referenceType = referenceType; }
    
    public String getActionUrl() { return actionUrl; }
    public void setActionUrl(String actionUrl) { this.actionUrl = actionUrl; }
    
    public Boolean getIsRead() { return isRead; }
    public void setIsRead(Boolean isRead) { this.isRead = isRead; }
    
    public LocalDateTime getReadAt() { return readAt; }
    public void setReadAt(LocalDateTime readAt) { this.readAt = readAt; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}