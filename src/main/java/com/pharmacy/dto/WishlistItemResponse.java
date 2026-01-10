package com.pharmacy.dto;

import java.time.LocalDateTime;

import com.pharmacy.entity.WishlistItem;
import com.user.response.MedicineResponse;

public class WishlistItemResponse {
    
    private Long id;
    private MedicineResponse medicine;
    private LocalDateTime createdAt;
    
    // Constructors
    public WishlistItemResponse() {}
    
    public WishlistItemResponse(WishlistItem wishlistItem) {
        this.id = wishlistItem.getId();
        this.medicine = new MedicineResponse(wishlistItem.getMedicine());
        this.createdAt = wishlistItem.getCreatedAt();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public MedicineResponse getMedicine() { return medicine; }
    public void setMedicine(MedicineResponse medicine) { this.medicine = medicine; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}