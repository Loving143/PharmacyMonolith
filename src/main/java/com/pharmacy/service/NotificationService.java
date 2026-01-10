package com.pharmacy.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pharmacy.entity.Notification;
import com.pharmacy.entity.Notification.NotificationType;

public interface NotificationService {
    
    Notification createNotification(String userName, String title, String message, NotificationType type);
    
    Notification createNotification(String userName, String title, String message, NotificationType type, 
                                  String referenceId, String referenceType, String actionUrl);
    
    Page<Notification> getUserNotifications(String userName, Pageable pageable);
    
    List<Notification> getUnreadNotifications(String userName);
    
    Long getUnreadNotificationCount(String userName);
    
    Notification markAsRead(Long notificationId);
    
    void markAllAsRead(String userName);
    
    void deleteNotification(Long notificationId);
    
    // Order related notifications
    void sendOrderConfirmationNotification(String userName, String orderNumber);
    
    void sendOrderShippedNotification(String userName, String orderNumber, String trackingNumber);
    
    void sendOrderDeliveredNotification(String userName, String orderNumber);
    
    void sendOrderCancelledNotification(String userName, String orderNumber, String reason);
    
    // Payment related notifications
    void sendPaymentSuccessNotification(String userName, String orderNumber, Double amount);
    
    void sendPaymentFailedNotification(String userName, String orderNumber, String reason);
    
    // Prescription related notifications
    void sendPrescriptionUploadedNotification(String userName);
    
    void sendPrescriptionVerifiedNotification(String userName, String prescriptionId);
    
    void sendPrescriptionRejectedNotification(String userName, String prescriptionId, String reason);
    
    // Medicine related notifications
    void sendMedicineOutOfStockNotification(String userName, String medicineName);
    
    void sendMedicineExpiryAlertNotification(String userName, String medicineName);
}