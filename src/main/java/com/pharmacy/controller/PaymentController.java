package com.pharmacy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pharmacy.dto.PaymentResponse;
import com.pharmacy.entity.Payment.PaymentMethod;
import com.pharmacy.entity.Payment.PaymentStatus;
import com.pharmacy.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    
    @Autowired
    private PaymentService paymentService;
    
    @PostMapping("/initiate")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PaymentResponse> initiatePayment(
            @RequestParam Long orderId,
            @RequestParam PaymentMethod paymentMethod) {
        PaymentResponse payment = paymentService.initiatePayment(orderId, paymentMethod);
        return ResponseEntity.ok(payment);
    }
    
    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(
            @RequestParam String paymentId,
            @RequestParam String transactionId,
            @RequestParam PaymentStatus status,
            @RequestParam(required = false) String gatewayResponse) {
        PaymentResponse payment = paymentService.processPayment(paymentId, transactionId, status, gatewayResponse);
        return ResponseEntity.ok(payment);
    }
    
    @GetMapping("/{paymentId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Long paymentId) {
        PaymentResponse payment = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(payment);
    }
    
    @GetMapping("/payment-id/{paymentId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<PaymentResponse> getPaymentByPaymentId(@PathVariable String paymentId) {
        PaymentResponse payment = paymentService.getPaymentByPaymentId(paymentId);
        return ResponseEntity.ok(payment);
    }
    
    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<PaymentResponse> getPaymentByOrderId(@PathVariable Long orderId) {
        PaymentResponse payment = paymentService.getPaymentByOrderId(orderId);
        return ResponseEntity.ok(payment);
    }
    
    @GetMapping("/user/{userName}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<PaymentResponse>> getUserPayments(@PathVariable String userName) {
        List<PaymentResponse> payments = paymentService.getUserPayments(userName);
        return ResponseEntity.ok(payments);
    }
    
    @GetMapping("/admin/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByStatus(@PathVariable PaymentStatus status) {
        List<PaymentResponse> payments = paymentService.getPaymentsByStatus(status);
        return ResponseEntity.ok(payments);
    }
    
    @PostMapping("/refund")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaymentResponse> refundPayment(
            @RequestParam String paymentId,
            @RequestParam Double refundAmount,
            @RequestParam String reason) {
        PaymentResponse payment = paymentService.refundPayment(paymentId, refundAmount, reason);
        return ResponseEntity.ok(payment);
    }
    
    @PutMapping("/update-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaymentResponse> updatePaymentStatus(
            @RequestParam String paymentId,
            @RequestParam PaymentStatus status,
            @RequestParam(required = false) String reason) {
        PaymentResponse payment = paymentService.updatePaymentStatus(paymentId, status, reason);
        return ResponseEntity.ok(payment);
    }
}