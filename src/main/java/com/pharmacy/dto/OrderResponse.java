package com.pharmacy.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.pharmacy.entity.Order;
import com.pharmacy.entity.Order.OrderStatus;
import com.user.response.AddressResponse;

public class OrderResponse {
    
    private Long id;
    private String orderNumber;
    private String userName;
    private AddressResponse deliveryAddress;
    private List<OrderItemResponse> orderItems;
    private PaymentResponse payment;
    private OrderStatus status;
    private Double subtotal;
    private Double taxAmount;
    private Double deliveryCharge;
    private Double discountAmount;
    private Double totalAmount;
    private Boolean prescriptionRequired;
    private Boolean prescriptionVerified;
    private LocalDateTime estimatedDeliveryDate;
    private LocalDateTime actualDeliveryDate;
    private String trackingNumber;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public OrderResponse() {}
    
    public OrderResponse(Order order) {
        this.id = order.getId();
        this.orderNumber = order.getOrderNumber();
        this.userName = order.getUserProfile().getUserName();
        this.deliveryAddress = new AddressResponse(order.getDeliveryAddress());
        this.orderItems = order.getOrderItems().stream()
            .map(OrderItemResponse::new)
            .collect(Collectors.toList());
        this.payment = order.getPayment() != null ? new PaymentResponse(order.getPayment()) : null;
        this.status = order.getStatus();
        this.subtotal = order.getSubtotal();
        this.taxAmount = order.getTaxAmount();
        this.deliveryCharge = order.getDeliveryCharge();
        this.discountAmount = order.getDiscountAmount();
        this.totalAmount = order.getTotalAmount();
        this.prescriptionRequired = order.getPrescriptionRequired();
        this.prescriptionVerified = order.getPrescriptionVerified();
        this.estimatedDeliveryDate = order.getEstimatedDeliveryDate();
        this.actualDeliveryDate = order.getActualDeliveryDate();
        this.trackingNumber = order.getTrackingNumber();
        this.notes = order.getNotes();
        this.createdAt = order.getCreatedAt();
        this.updatedAt = order.getUpdatedAt();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public AddressResponse getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(AddressResponse deliveryAddress) { this.deliveryAddress = deliveryAddress; }
    
    public List<OrderItemResponse> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItemResponse> orderItems) { this.orderItems = orderItems; }
    
    public PaymentResponse getPayment() { return payment; }
    public void setPayment(PaymentResponse payment) { this.payment = payment; }
    
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    
    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
    
    public Double getTaxAmount() { return taxAmount; }
    public void setTaxAmount(Double taxAmount) { this.taxAmount = taxAmount; }
    
    public Double getDeliveryCharge() { return deliveryCharge; }
    public void setDeliveryCharge(Double deliveryCharge) { this.deliveryCharge = deliveryCharge; }
    
    public Double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(Double discountAmount) { this.discountAmount = discountAmount; }
    
    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    
    public Boolean getPrescriptionRequired() { return prescriptionRequired; }
    public void setPrescriptionRequired(Boolean prescriptionRequired) { this.prescriptionRequired = prescriptionRequired; }
    
    public Boolean getPrescriptionVerified() { return prescriptionVerified; }
    public void setPrescriptionVerified(Boolean prescriptionVerified) { this.prescriptionVerified = prescriptionVerified; }
    
    public LocalDateTime getEstimatedDeliveryDate() { return estimatedDeliveryDate; }
    public void setEstimatedDeliveryDate(LocalDateTime estimatedDeliveryDate) { this.estimatedDeliveryDate = estimatedDeliveryDate; }
    
    public LocalDateTime getActualDeliveryDate() { return actualDeliveryDate; }
    public void setActualDeliveryDate(LocalDateTime actualDeliveryDate) { this.actualDeliveryDate = actualDeliveryDate; }
    
    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}