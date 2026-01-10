package com.pharmacy.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharmacy.entity.Order;
import com.pharmacy.entity.Order.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    Optional<Order> findByOrderNumber(String orderNumber);
    
    @Query("SELECT o FROM Order o WHERE o.userProfile.userName = :userName ORDER BY o.createdAt DESC")
    Page<Order> findByUserNameOrderByCreatedAtDesc(@Param("userName") String userName, Pageable pageable);
    
    @Query("SELECT o FROM Order o WHERE o.userProfile.userName = :userName AND o.status = :status ORDER BY o.createdAt DESC")
    List<Order> findByUserNameAndStatus(@Param("userName") String userName, @Param("status") OrderStatus status);
    
    @Query("SELECT o FROM Order o WHERE o.status = :status ORDER BY o.createdAt DESC")
    Page<Order> findByStatusOrderByCreatedAtDesc(@Param("status") OrderStatus status, Pageable pageable);
    
    @Query("SELECT o FROM Order o WHERE o.createdAt BETWEEN :startDate AND :endDate ORDER BY o.createdAt DESC")
    List<Order> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(o) FROM Order o WHERE o.userProfile.userName = :userName")
    Long countByUserName(@Param("userName") String userName);
    
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.userProfile.userName = :userName AND o.status = 'DELIVERED'")
    Double getTotalSpentByUserName(@Param("userName") String userName);
    
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderItems oi LEFT JOIN FETCH oi.medicine WHERE o.orderNumber = :orderNumber")
    Optional<Order> findByOrderNumberWithItems(@Param("orderNumber") String orderNumber);
}