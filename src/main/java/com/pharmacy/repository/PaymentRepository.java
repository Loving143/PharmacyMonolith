package com.pharmacy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharmacy.entity.Payment;
import com.pharmacy.entity.Payment.PaymentStatus;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    Optional<Payment> findByPaymentId(String paymentId);
    
    Optional<Payment> findByTransactionId(String transactionId);
    
    @Query("SELECT p FROM Payment p WHERE p.order.id = :orderId")
    Optional<Payment> findByOrderId(@Param("orderId") Long orderId);
    
    @Query("SELECT p FROM Payment p WHERE p.order.orderNumber = :orderNumber")
    Optional<Payment> findByOrderNumber(@Param("orderNumber") String orderNumber);
    
    @Query("SELECT p FROM Payment p WHERE p.paymentStatus = :status ORDER BY p.createdAt DESC")
    List<Payment> findByPaymentStatus(@Param("status") PaymentStatus status);
    
    @Query("SELECT p FROM Payment p WHERE p.order.userProfile.userName = :userName ORDER BY p.createdAt DESC")
    List<Payment> findByUserName(@Param("userName") String userName);
}