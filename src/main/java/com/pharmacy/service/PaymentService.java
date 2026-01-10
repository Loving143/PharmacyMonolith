package com.pharmacy.service;

import java.util.List;

import com.pharmacy.dto.PaymentResponse;
import com.pharmacy.entity.Payment.PaymentMethod;
import com.pharmacy.entity.Payment.PaymentStatus;

public interface PaymentService {
    
    PaymentResponse initiatePayment(Long orderId, PaymentMethod paymentMethod);
    
    PaymentResponse processPayment(String paymentId, String transactionId, PaymentStatus status, String gatewayResponse);
    
    PaymentResponse getPaymentById(Long paymentId);
    
    PaymentResponse getPaymentByPaymentId(String paymentId);
    
    PaymentResponse getPaymentByOrderId(Long orderId);
    
    List<PaymentResponse> getUserPayments(String userName);
    
    List<PaymentResponse> getPaymentsByStatus(PaymentStatus status);
    
    PaymentResponse refundPayment(String paymentId, Double refundAmount, String reason);
    
    PaymentResponse updatePaymentStatus(String paymentId, PaymentStatus status, String reason);
}