package com.pharmacy.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pharmacy.entity.Notification;
import com.pharmacy.entity.Notification.NotificationType;
import com.pharmacy.repository.NotificationRepository;
import com.pharmacy.service.NotificationService;
import com.user.entity.UserProfiles;
import com.user.repository.UserProfileRepository;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    @Autowired
    private UserProfileRepository userProfileRepository;
    
    @Override
    public Notification createNotification(String userName, String title, String message, NotificationType type) {
        return createNotification(userName, title, message, type, null, null, null);
    }
    
    @Override
    public Notification createNotification(String userName, String title, String message, 
                                         NotificationType type, String referenceId, 
                                         String referenceType, String actionUrl) {
        UserProfiles userProfile = userProfileRepository.findByUserName(userName)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Notification notification = new Notification(userProfile, title, message, type);
        notification.setReferenceId(referenceId);
        notification.setReferenceType(referenceType);
        notification.setActionUrl(actionUrl);
        
        return notificationRepository.save(notification);
    }
    
    @Override
    public Page<Notification> getUserNotifications(String userName, Pageable pageable) {
        return notificationRepository.findByUserNameOrderByCreatedAtDesc(userName, pageable);
    }
    
    @Override
    public List<Notification> getUnreadNotifications(String userName) {
        return notificationRepository.findByUserNameAndStatus(userName, Notification.NotificationStatus.UNREAD);
    }
    
    @Override
    public Long getUnreadNotificationCount(String userName) {
        return notificationRepository.countUnreadByUserName(userName);
    }
    
    @Override
    public Notification markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new RuntimeException("Notification not found"));
        
        notification.markAsRead();
        return notificationRepository.save(notification);
    }
    
    @Override
    public void markAllAsRead(String userName) {
        notificationRepository.markAllAsReadByUserName(userName);
    }
    
    @Override
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }
    
    // Order related notifications
    @Override
    public void sendOrderConfirmationNotification(String userName, String orderNumber) {
        createNotification(
            userName,
            "Order Confirmed",
            "Your order " + orderNumber + " has been confirmed and is being processed.",
            NotificationType.ORDER_CONFIRMATION,
            orderNumber,
            "ORDER",
            "/orders/" + orderNumber
        );
    }
    
    @Override
    public void sendOrderShippedNotification(String userName, String orderNumber, String trackingNumber) {
        String message = "Your order " + orderNumber + " has been shipped.";
        if (trackingNumber != null) {
            message += " Tracking number: " + trackingNumber;
        }
        
        createNotification(
            userName,
            "Order Shipped",
            message,
            NotificationType.ORDER_SHIPPED,
            orderNumber,
            "ORDER",
            "/orders/" + orderNumber + "/track"
        );
    }
    
    @Override
    public void sendOrderDeliveredNotification(String userName, String orderNumber) {
        createNotification(
            userName,
            "Order Delivered",
            "Your order " + orderNumber + " has been delivered successfully.",
            NotificationType.ORDER_DELIVERED,
            orderNumber,
            "ORDER",
            "/orders/" + orderNumber
        );
    }
    
    @Override
    public void sendOrderCancelledNotification(String userName, String orderNumber, String reason) {
        String message = "Your order " + orderNumber + " has been cancelled.";
        if (reason != null) {
            message += " Reason: " + reason;
        }
        
        createNotification(
            userName,
            "Order Cancelled",
            message,
            NotificationType.ORDER_CANCELLED,
            orderNumber,
            "ORDER",
            "/orders/" + orderNumber
        );
    }
    
    // Payment related notifications
    @Override
    public void sendPaymentSuccessNotification(String userName, String orderNumber, Double amount) {
        createNotification(
            userName,
            "Payment Successful",
            "Payment of ₹" + amount + " for order " + orderNumber + " was successful.",
            NotificationType.PAYMENT_SUCCESS,
            orderNumber,
            "PAYMENT",
            "/orders/" + orderNumber
        );
    }
    
    @Override
    public void sendPaymentFailedNotification(String userName, String orderNumber, String reason) {
        String message = "Payment for order " + orderNumber + " failed.";
        if (reason != null) {
            message += " Reason: " + reason;
        }
        
        createNotification(
            userName,
            "Payment Failed",
            message,
            NotificationType.PAYMENT_FAILED,
            orderNumber,
            "PAYMENT",
            "/orders/" + orderNumber + "/retry-payment"
        );
    }
    
    // Prescription related notifications
    @Override
    public void sendPrescriptionUploadedNotification(String userName) {
        createNotification(
            userName,
            "Prescription Uploaded",
            "Your prescription has been uploaded successfully and is under review.",
            NotificationType.PRESCRIPTION_UPLOADED,
            null,
            "PRESCRIPTION",
            "/prescriptions"
        );
    }
    
    @Override
    public void sendPrescriptionVerifiedNotification(String userName, String prescriptionId) {
        createNotification(
            userName,
            "Prescription Verified",
            "Your prescription has been verified by our pharmacist.",
            NotificationType.PRESCRIPTION_VERIFIED,
            prescriptionId,
            "PRESCRIPTION",
            "/prescriptions/" + prescriptionId
        );
    }
    
    @Override
    public void sendPrescriptionRejectedNotification(String userName, String prescriptionId, String reason) {
        String message = "Your prescription has been rejected.";
        if (reason != null) {
            message += " Reason: " + reason;
        }
        
        createNotification(
            userName,
            "Prescription Rejected",
            message,
            NotificationType.PRESCRIPTION_REJECTED,
            prescriptionId,
            "PRESCRIPTION",
            "/prescriptions/" + prescriptionId
        );
    }
    
    // Medicine related notifications
    @Override
    public void sendMedicineOutOfStockNotification(String userName, String medicineName) {
        createNotification(
            userName,
            "Medicine Out of Stock",
            "The medicine " + medicineName + " in your wishlist is currently out of stock.",
            NotificationType.MEDICINE_OUT_OF_STOCK,
            null,
            "MEDICINE",
            "/wishlist"
        );
    }
    
    @Override
    public void sendMedicineExpiryAlertNotification(String userName, String medicineName) {
        createNotification(
            userName,
            "Medicine Expiry Alert",
            "The medicine " + medicineName + " is nearing its expiry date.",
            NotificationType.MEDICINE_EXPIRY_ALERT,
            null,
            "MEDICINE",
            "/medicines"
        );
    }
}