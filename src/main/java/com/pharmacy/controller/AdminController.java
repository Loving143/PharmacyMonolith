package com.pharmacy.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pharmacy.dto.OrderResponse;
import com.pharmacy.dto.PaymentResponse;
import com.pharmacy.entity.Order.OrderStatus;
import com.pharmacy.entity.Payment.PaymentStatus;
import com.pharmacy.service.NotificationService;
import com.pharmacy.service.OrderService;
import com.pharmacy.service.PaymentService;
import com.user.response.MedicineResponse;
import com.user.response.UserProfileResponse;
import com.user.service.MedicineService;
import com.user.service.UserService;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private MedicineService medicineService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private NotificationService notificationService;
    
    // Dashboard Analytics
    @GetMapping("/dashboard/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        // Get orders by status
        Page<OrderResponse> pendingOrders = orderService.getOrdersByStatus(OrderStatus.PENDING, PageRequest.of(0, 1));
        Page<OrderResponse> confirmedOrders = orderService.getOrdersByStatus(OrderStatus.CONFIRMED, PageRequest.of(0, 1));
        Page<OrderResponse> shippedOrders = orderService.getOrdersByStatus(OrderStatus.SHIPPED, PageRequest.of(0, 1));
        Page<OrderResponse> deliveredOrders = orderService.getOrdersByStatus(OrderStatus.DELIVERED, PageRequest.of(0, 1));
        
        stats.put("pendingOrdersCount", pendingOrders.getTotalElements());
        stats.put("confirmedOrdersCount", confirmedOrders.getTotalElements());
        stats.put("shippedOrdersCount", shippedOrders.getTotalElements());
        stats.put("deliveredOrdersCount", deliveredOrders.getTotalElements());
        
        // Get payment stats
        List<PaymentResponse> successfulPayments = paymentService.getPaymentsByStatus(PaymentStatus.SUCCESS);
        List<PaymentResponse> failedPayments = paymentService.getPaymentsByStatus(PaymentStatus.FAILED);
        
        stats.put("successfulPaymentsCount", successfulPayments.size());
        stats.put("failedPaymentsCount", failedPayments.size());
        
        // Get medicine stats
        List<MedicineResponse> activeMedicines = medicineService.findAllActiveMedicines();
        List<MedicineResponse> expiredMedicines = medicineService.findExpiredMedicines();
        
        stats.put("activeMedicinesCount", activeMedicines.size());
        stats.put("expiredMedicinesCount", expiredMedicines.size());
        
        stats.put("lastUpdated", LocalDateTime.now());
        
        return ResponseEntity.ok(stats);
    }
    
    // Order Management
    @GetMapping("/orders")
    public ResponseEntity<Page<OrderResponse>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderResponse> orders = orderService.getAllOrders(pageable);
        return ResponseEntity.ok(orders);
    }
    
    @GetMapping("/orders/status/{status}")
    public ResponseEntity<Page<OrderResponse>> getOrdersByStatus(
            @PathVariable OrderStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderResponse> orders = orderService.getOrdersByStatus(status, pageable);
        return ResponseEntity.ok(orders);
    }
    
    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {
        OrderResponse order = orderService.updateOrderStatus(orderId, status);
        
        // Send notification based on status
        switch (status) {
            case CONFIRMED:
                notificationService.sendOrderConfirmationNotification(
                    order.getUserName(), order.getOrderNumber());
                break;
            case SHIPPED:
                notificationService.sendOrderShippedNotification(
                    order.getUserName(), order.getOrderNumber(), order.getTrackingNumber());
                break;
            case DELIVERED:
                notificationService.sendOrderDeliveredNotification(
                    order.getUserName(), order.getOrderNumber());
                break;
            case CANCELLED:
                notificationService.sendOrderCancelledNotification(
                    order.getUserName(), order.getOrderNumber(), "Cancelled by admin");
                break;
            default:
                break;
        }
        
        return ResponseEntity.ok(order);
    }
    
    @PutMapping("/orders/{orderId}/tracking")
    public ResponseEntity<OrderResponse> addTrackingNumber(
            @PathVariable Long orderId,
            @RequestParam String trackingNumber) {
        OrderResponse order = orderService.addTrackingNumber(orderId, trackingNumber);
        return ResponseEntity.ok(order);
    }
    
    // Payment Management
    @GetMapping("/payments")
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {
        List<PaymentResponse> payments = paymentService.getPaymentsByStatus(PaymentStatus.SUCCESS);
        return ResponseEntity.ok(payments);
    }
    
    @GetMapping("/payments/status/{status}")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByStatus(@PathVariable PaymentStatus status) {
        List<PaymentResponse> payments = paymentService.getPaymentsByStatus(status);
        return ResponseEntity.ok(payments);
    }
    
    @PutMapping("/payments/{paymentId}/refund")
    public ResponseEntity<PaymentResponse> processRefund(
            @PathVariable String paymentId,
            @RequestParam Double refundAmount,
            @RequestParam String reason) {
        PaymentResponse payment = paymentService.refundPayment(paymentId, refundAmount, reason);
        return ResponseEntity.ok(payment);
    }
    
    // Medicine Management
    @GetMapping("/medicines/expired")
    public ResponseEntity<List<MedicineResponse>> getExpiredMedicines() {
        List<MedicineResponse> expiredMedicines = medicineService.findExpiredMedicines();
        return ResponseEntity.ok(expiredMedicines);
    }
    
    @GetMapping("/medicines/low-stock")
    public ResponseEntity<List<MedicineResponse>> getLowStockMedicines() {
        // This would need to be implemented in MedicineService
        // For now, returning active medicines as placeholder
        List<MedicineResponse> medicines = medicineService.findAllActiveMedicines();
        return ResponseEntity.ok(medicines);
    }
    
    // User Management
    @GetMapping("/users/search")
    public ResponseEntity<List<UserProfileResponse>> searchUsers(@RequestParam String query) {
        // This would need to be implemented in UserService
        // For now, returning empty list as placeholder
        return ResponseEntity.ok(List.of());
    }
    
    // Reports
    @GetMapping("/reports/sales")
    public ResponseEntity<Map<String, Object>> getSalesReport(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        Map<String, Object> report = new HashMap<>();
        
        // This would need proper implementation with date filtering
        Page<OrderResponse> deliveredOrders = orderService.getOrdersByStatus(OrderStatus.DELIVERED, PageRequest.of(0, 100));
        
        double totalRevenue = deliveredOrders.getContent().stream()
            .mapToDouble(OrderResponse::getTotalAmount)
            .sum();
        
        report.put("totalOrders", deliveredOrders.getTotalElements());
        report.put("totalRevenue", totalRevenue);
        report.put("averageOrderValue", totalRevenue / deliveredOrders.getTotalElements());
        report.put("reportGeneratedAt", LocalDateTime.now());
        
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/reports/inventory")
    public ResponseEntity<Map<String, Object>> getInventoryReport() {
        Map<String, Object> report = new HashMap<>();
        
        List<MedicineResponse> activeMedicines = medicineService.findAllActiveMedicines();
        List<MedicineResponse> expiredMedicines = medicineService.findExpiredMedicines();
        
        report.put("totalActiveMedicines", activeMedicines.size());
        report.put("totalExpiredMedicines", expiredMedicines.size());
        report.put("reportGeneratedAt", LocalDateTime.now());
        
        return ResponseEntity.ok(report);
    }
}