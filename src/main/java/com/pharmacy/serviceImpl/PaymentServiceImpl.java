package com.pharmacy.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pharmacy.dto.PaymentResponse;
import com.pharmacy.entity.Order;
import com.pharmacy.entity.Payment;
import com.pharmacy.entity.Payment.PaymentMethod;
import com.pharmacy.entity.Payment.PaymentStatus;
import com.pharmacy.entity.Payment.RefundStatus;
import com.pharmacy.repository.OrderRepository;
import com.pharmacy.repository.PaymentRepository;
import com.pharmacy.service.NotificationService;
import com.pharmacy.service.PaymentService;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private NotificationService notificationService;
    
    @Override
    public PaymentResponse initiatePayment(Long orderId, PaymentMethod paymentMethod) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        
        // Check if payment already exists
        if (paymentRepository.findByOrderId(orderId).isPresent()) {
            throw new RuntimeException("Payment already initiated for this order");
        }
        
        Payment payment = new Payment(order, paymentMethod, order.getTotalAmount());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        
        Payment savedPayment = paymentRepository.save(payment);
        return new PaymentResponse(savedPayment);
    }
    
    @Override
    public PaymentResponse processPayment(String paymentId, String transactionId, 
                                        PaymentStatus status, String gatewayResponse) {
        Payment payment = paymentRepository.findByPaymentId(paymentId)
            .orElseThrow(() -> new RuntimeException("Payment not found"));
        
        payment.setTransactionId(transactionId);
        payment.setPaymentStatus(status);
        payment.setGatewayResponse(gatewayResponse);
        
        if (status == PaymentStatus.SUCCESS) {
            payment.setPaymentDate(LocalDateTime.now());
            
            // Update order status
            Order order = payment.getOrder();
            order.setStatus(Order.OrderStatus.CONFIRMED);
            orderRepository.save(order);
            
            // Send success notification
            notificationService.sendPaymentSuccessNotification(
                order.getUserProfile().getUserName(), 
                order.getOrderNumber(), 
                payment.getAmount());
        } else if (status == PaymentStatus.FAILED) {
            // Send failure notification
            notificationService.sendPaymentFailedNotification(
                payment.getOrder().getUserProfile().getUserName(),
                payment.getOrder().getOrderNumber(),
                gatewayResponse);
        }
        
        Payment savedPayment = paymentRepository.save(payment);
        return new PaymentResponse(savedPayment);
    }
    
    @Override
    public PaymentResponse getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> new RuntimeException("Payment not found"));
        return new PaymentResponse(payment);
    }
    
    @Override
    public PaymentResponse getPaymentByPaymentId(String paymentId) {
        Payment payment = paymentRepository.findByPaymentId(paymentId)
            .orElseThrow(() -> new RuntimeException("Payment not found"));
        return new PaymentResponse(payment);
    }
    
    @Override
    public PaymentResponse getPaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
            .orElseThrow(() -> new RuntimeException("Payment not found for this order"));
        return new PaymentResponse(payment);
    }
    
    @Override
    public List<PaymentResponse> getUserPayments(String userName) {
        List<Payment> payments = paymentRepository.findByUserName(userName);
        return payments.stream().map(PaymentResponse::new).collect(Collectors.toList());
    }
    
    @Override
    public List<PaymentResponse> getPaymentsByStatus(PaymentStatus status) {
        List<Payment> payments = paymentRepository.findByPaymentStatus(status);
        return payments.stream().map(PaymentResponse::new).collect(Collectors.toList());
    }
    
    @Override
    public PaymentResponse refundPayment(String paymentId, Double refundAmount, String reason) {
        Payment payment = paymentRepository.findByPaymentId(paymentId)
            .orElseThrow(() -> new RuntimeException("Payment not found"));
        
        if (payment.getPaymentStatus() != PaymentStatus.SUCCESS) {
            throw new RuntimeException("Can only refund successful payments");
        }
        
        if (refundAmount > payment.getAmount()) {
            throw new RuntimeException("Refund amount cannot exceed payment amount");
        }
        
        payment.setRefundAmount(refundAmount);
        payment.setRefundStatus(RefundStatus.PROCESSING);
        payment.setRefundDate(LocalDateTime.now());
        payment.setFailureReason(reason); // Using this field to store refund reason
        
        // If full refund, update payment status
        if (refundAmount.equals(payment.getAmount())) {
            payment.setPaymentStatus(PaymentStatus.REFUNDED);
            payment.setRefundStatus(RefundStatus.SUCCESS);
        }
        
        Payment savedPayment = paymentRepository.save(payment);
        return new PaymentResponse(savedPayment);
    }
    
    @Override
    public PaymentResponse updatePaymentStatus(String paymentId, PaymentStatus status, String reason) {
        Payment payment = paymentRepository.findByPaymentId(paymentId)
            .orElseThrow(() -> new RuntimeException("Payment not found"));
        
        payment.setPaymentStatus(status);
        if (reason != null) {
            payment.setFailureReason(reason);
        }
        
        if (status == PaymentStatus.SUCCESS && payment.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDateTime.now());
        }
        
        Payment savedPayment = paymentRepository.save(payment);
        return new PaymentResponse(savedPayment);
    }
}