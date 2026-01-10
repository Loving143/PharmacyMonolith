package com.pharmacy.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pharmacy.dto.OrderRequest;
import com.pharmacy.dto.OrderResponse;
import com.pharmacy.entity.Cart;
import com.pharmacy.entity.Order;
import com.pharmacy.entity.OrderItem;
import com.pharmacy.entity.Order.OrderStatus;
import com.pharmacy.repository.CartRepository;
import com.pharmacy.repository.OrderRepository;
import com.pharmacy.service.NotificationService;
import com.pharmacy.service.OrderService;
import com.user.entity.Address;
import com.user.entity.Medicine;
import com.user.entity.UserProfiles;
import com.user.repository.AddressRepository;
import com.user.repository.MedicineRepository;
import com.user.repository.UserProfileRepository;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private UserProfileRepository userProfileRepository;
    
    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    private MedicineRepository medicineRepository;
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private NotificationService notificationService;
    
    @Override
    public OrderResponse createOrder(OrderRequest request) {
        UserProfiles userProfile = userProfileRepository.findByUserName(request.getUserName())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Address deliveryAddress = addressRepository.findById(Math.toIntExact(request.getDeliveryAddressId()))
            .orElseThrow(() -> new RuntimeException("Address not found"));
        
        Order order = new Order(userProfile, deliveryAddress);
        order.setNotes(request.getNotes());
        
        // Add order items
        if (request.getOrderItems() != null) {
            for (OrderRequest.OrderItemRequest itemRequest : request.getOrderItems()) {
                Medicine medicine = medicineRepository.findById(itemRequest.getMedicineId())
                    .orElseThrow(() -> new RuntimeException("Medicine not found"));
                
                Double unitPrice = "STRIP".equals(itemRequest.getUnitType()) 
                    ? medicine.getSellingCostStrip() 
                    : medicine.getSellingCostTablet();
                
                OrderItem orderItem = new OrderItem(order, medicine, itemRequest.getQuantity(), 
                    unitPrice, itemRequest.getUnitType());
                order.addOrderItem(orderItem);
            }
        }
        
        // Set delivery charge (example logic)
        order.setDeliveryCharge(order.getSubtotal() > 500 ? 0.0 : 50.0);
        
        // Set estimated delivery date (example: 3 days from now)
        order.setEstimatedDeliveryDate(LocalDateTime.now().plusDays(3));
        
        order.calculateTotals();
        Order savedOrder = orderRepository.save(order);
        
        // Send notification
        notificationService.sendOrderConfirmationNotification(
            request.getUserName(), savedOrder.getOrderNumber());
        
        return new OrderResponse(savedOrder);
    }
    
    @Override
    public OrderResponse createOrderFromCart(String userName, Long deliveryAddressId, String paymentMethod) {
        Cart cart = cartRepository.findByUserNameWithItems(userName)
            .orElseThrow(() -> new RuntimeException("Cart not found or empty"));
        
        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        
        Address deliveryAddress = addressRepository.findById(Math.toIntExact(deliveryAddressId))
            .orElseThrow(() -> new RuntimeException("Address not found"));
        
        Order order = new Order(cart.getUserProfile(), deliveryAddress);
        
        // Convert cart items to order items
        cart.getCartItems().forEach(cartItem -> {
            OrderItem orderItem = new OrderItem(order, cartItem.getMedicine(), 
                cartItem.getQuantity(), cartItem.getUnitPrice(), cartItem.getUnitType());
            order.addOrderItem(orderItem);
        });
        
        // Set delivery charge
        order.setDeliveryCharge(order.getSubtotal() > 500 ? 0.0 : 50.0);
        order.setEstimatedDeliveryDate(LocalDateTime.now().plusDays(3));
        
        order.calculateTotals();
        Order savedOrder = orderRepository.save(order);
        
        // Clear cart after order creation
        cart.getCartItems().clear();
        cart.calculateTotals();
        cartRepository.save(cart);
        
        // Send notification
        notificationService.sendOrderConfirmationNotification(userName, savedOrder.getOrderNumber());
        
        return new OrderResponse(savedOrder);
    }
    
    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        return new OrderResponse(order);
    }
    
    @Override
    public OrderResponse getOrderByOrderNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumberWithItems(orderNumber)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        return new OrderResponse(order);
    }
    
    @Override
    public Page<OrderResponse> getUserOrders(String userName, Pageable pageable) {
        Page<Order> orders = orderRepository.findByUserNameOrderByCreatedAtDesc(userName, pageable);
        return orders.map(OrderResponse::new);
    }
    
    @Override
    public List<OrderResponse> getUserOrdersByStatus(String userName, OrderStatus status) {
        List<Order> orders = orderRepository.findByUserNameAndStatus(userName, status);
        return orders.stream().map(OrderResponse::new).collect(Collectors.toList());
    }
    
    @Override
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setStatus(status);
        
        if (status == OrderStatus.DELIVERED) {
            order.setActualDeliveryDate(LocalDateTime.now());
        }
        
        Order savedOrder = orderRepository.save(order);
        return new OrderResponse(savedOrder);
    }
    
    @Override
    public OrderResponse cancelOrder(Long orderId, String reason) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        
        if (order.getStatus() == OrderStatus.DELIVERED || order.getStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Cannot cancel order in current status");
        }
        
        order.setStatus(OrderStatus.CANCELLED);
        order.setNotes(order.getNotes() + "\nCancellation reason: " + reason);
        
        Order savedOrder = orderRepository.save(order);
        
        // Send notification
        notificationService.sendOrderCancelledNotification(
            order.getUserProfile().getUserName(), order.getOrderNumber(), reason);
        
        return new OrderResponse(savedOrder);
    }
    
    @Override
    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(OrderResponse::new);
    }
    
    @Override
    public Page<OrderResponse> getOrdersByStatus(OrderStatus status, Pageable pageable) {
        Page<Order> orders = orderRepository.findByStatusOrderByCreatedAtDesc(status, pageable);
        return orders.map(OrderResponse::new);
    }
    
    @Override
    public OrderResponse addTrackingNumber(Long orderId, String trackingNumber) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setTrackingNumber(trackingNumber);
        Order savedOrder = orderRepository.save(order);
        
        return new OrderResponse(savedOrder);
    }
    
    @Override
    public Long getUserOrderCount(String userName) {
        return orderRepository.countByUserName(userName);
    }
    
    @Override
    public Double getUserTotalSpent(String userName) {
        Double totalSpent = orderRepository.getTotalSpentByUserName(userName);
        return totalSpent != null ? totalSpent : 0.0;
    }
}