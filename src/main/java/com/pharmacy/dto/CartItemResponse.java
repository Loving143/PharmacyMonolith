package com.pharmacy.dto;

import java.time.LocalDateTime;

import com.pharmacy.entity.CartItem;
import com.user.response.MedicineResponse;

public class CartItemResponse {
    
    private Long id;
    private MedicineResponse medicine;
    private Integer quantity;
    private Double unitPrice;
    private String unitType;
    private Boolean prescriptionRequired;
    private Double totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public CartItemResponse() {}
    
    public CartItemResponse(CartItem cartItem) {
        this.id = cartItem.getId();
        this.medicine = new MedicineResponse(cartItem.getMedicine());
        this.quantity = cartItem.getQuantity();
        this.unitPrice = cartItem.getUnitPrice();
        this.unitType = cartItem.getUnitType();
        this.prescriptionRequired = cartItem.getPrescriptionRequired();
        this.totalPrice = cartItem.getTotalPrice();
        this.createdAt = cartItem.getCreatedAt();
        this.updatedAt = cartItem.getUpdatedAt();
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
    
    public Boolean getPrescriptionRequired() { return prescriptionRequired; }
    public void setPrescriptionRequired(Boolean prescriptionRequired) { this.prescriptionRequired = prescriptionRequired; }
    
    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}