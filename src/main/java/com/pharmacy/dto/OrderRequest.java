package com.pharmacy.dto;

import java.util.List;

import com.pharmacy.entity.Payment.PaymentMethod;

import jakarta.validation.constraints.NotNull;

public class OrderRequest {
    
    @NotNull(message = "User name is required")
    private String userName;
    
    @NotNull(message = "Delivery address ID is required")
    private Long deliveryAddressId;
    
    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;
    
    private List<OrderItemRequest> orderItems;
    
    private String notes;
    
    private String couponCode;
    
    // Constructors
    public OrderRequest() {}
    
    public OrderRequest(String userName, Long deliveryAddressId, PaymentMethod paymentMethod) {
        this.userName = userName;
        this.deliveryAddressId = deliveryAddressId;
        this.paymentMethod = paymentMethod;
    }
    
    // Getters and Setters
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public Long getDeliveryAddressId() { return deliveryAddressId; }
    public void setDeliveryAddressId(Long deliveryAddressId) { this.deliveryAddressId = deliveryAddressId; }
    
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    
    public List<OrderItemRequest> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItemRequest> orderItems) { this.orderItems = orderItems; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public String getCouponCode() { return couponCode; }
    public void setCouponCode(String couponCode) { this.couponCode = couponCode; }
    
    public static class OrderItemRequest {
        private Long medicineId;
        private Integer quantity;
        private String unitType;
        
        // Constructors
        public OrderItemRequest() {}
        
        public OrderItemRequest(Long medicineId, Integer quantity, String unitType) {
            this.medicineId = medicineId;
            this.quantity = quantity;
            this.unitType = unitType;
        }
        
        // Getters and Setters
        public Long getMedicineId() { return medicineId; }
        public void setMedicineId(Long medicineId) { this.medicineId = medicineId; }
        
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        
        public String getUnitType() { return unitType; }
        public void setUnitType(String unitType) { this.unitType = unitType; }
    }
}