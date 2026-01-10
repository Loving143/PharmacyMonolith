package com.pharmacy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharmacy.entity.Notification;
import com.pharmacy.entity.Notification.NotificationStatus;
import com.pharmacy.entity.Notification.NotificationType;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    @Query("SELECT n FROM Notification n WHERE n.userProfile.userName = :userName ORDER BY n.createdAt DESC")
    Page<Notification> findByUserNameOrderByCreatedAtDesc(@Param("userName") String userName, Pageable pageable);
    
    @Query("SELECT n FROM Notification n WHERE n.userProfile.userName = :userName AND n.status = :status ORDER BY n.createdAt DESC")
    List<Notification> findByUserNameAndStatus(@Param("userName") String userName, @Param("status") NotificationStatus status);
    
    @Query("SELECT n FROM Notification n WHERE n.userProfile.userName = :userName AND n.type = :type ORDER BY n.createdAt DESC")
    List<Notification> findByUserNameAndType(@Param("userName") String userName, @Param("type") NotificationType type);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.userProfile.userName = :userName AND n.isRead = false")
    Long countUnreadByUserName(@Param("userName") String userName);
    
    @Modifying
    @Query("UPDATE Notification n SET n.isRead = true, n.status = 'READ', n.readAt = CURRENT_TIMESTAMP WHERE n.userProfile.userName = :userName AND n.isRead = false")
    void markAllAsReadByUserName(@Param("userName") String userName);
    
    @Modifying
    @Query("UPDATE Notification n SET n.isRead = true, n.status = 'read', n.readAt = CURRENT_TIMESTAMP WHERE n.id = :id")
    void markAsRead(@Param("id") Long id);
}