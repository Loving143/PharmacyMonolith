package com.pharmacy.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pharmacy.dto.OrderRequest;
import com.pharmacy.dto.OrderResponse;
import com.pharmacy.entity.Order.OrderStatus;

public interface OrderService {
    
    OrderResponse createOrder(OrderRequest request);
    
    OrderResponse createOrderFromCart(String userName, Long deliveryAddressId, String paymentMethod);
    
    OrderResponse getOrderById(Long orderId);
    
    OrderResponse getOrderByOrderNumber(String orderNumber);
    
    Page<OrderResponse> getUserOrders(String userName, Pageable pageable);
    
    List<OrderResponse> getUserOrdersByStatus(String userName, OrderStatus status);
    
    OrderResponse updateOrderStatus(Long orderId, OrderStatus status);
    
    OrderResponse cancelOrder(Long orderId, String reason);
    
    Page<OrderResponse> getAllOrders(Pageable pageable);
    
    Page<OrderResponse> getOrdersByStatus(OrderStatus status, Pageable pageable);
    
    OrderResponse addTrackingNumber(Long orderId, String trackingNumber);
    
    Long getUserOrderCount(String userName);
    
    Double getUserTotalSpent(String userName);
}