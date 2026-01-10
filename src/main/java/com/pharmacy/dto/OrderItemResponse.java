package com.pharmacy.dto;

import java.time.LocalDateTime;

import com.pharmacy.entity.OrderItem;
import com.user.response.MedicineResponse;

public class OrderItemResponse {
    
    private Long id;
    private MedicineResponse medicine;
    private Integer quantity;
    private Double unitPrice;
    private String unitType;
    private Double discountAmount;
    private Double totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public OrderItemResponse() {}
    
    public OrderItemResponse(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.medicine = new MedicineResponse(orderItem.getMedicine());
        this.quantity = orderItem.getQuantity();
        this.unitPrice = orderItem.getUnitPrice();
        this.unitType = orderItem.getUnitType();
        this.discountAmount = orderItem.getDiscountAmount();
        this.totalPrice = orderItem.getTotalPrice();
        this.createdAt = orderItem.getCreatedAt();
        this.updatedAt = orderItem.getUpdatedAt();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public MedicineResponse getMedicine() { return medicine; }
    public void setMedicine(MedicineResponse medicine) { this.medicine = medicine; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public Double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }
    
    public String getUnitType() { return unitType; }
    public void setUnitType(String unitType) { this.unitType = unitType; }
    
    public Double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(Double discountAmount) { this.discountAmount = discountAmount; }
    
    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}