package com.pharmacy.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.pharmacy.entity.Wishlist;

public class WishlistResponse {
    
    private Long id;
    private String userName;
    private List<WishlistItemResponse> wishlistItems;
    private Integer totalItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public WishlistResponse() {}
    
    public WishlistResponse(Wishlist wishlist) {
        this.id = wishlist.getId();
        this.userName = wishlist.getUserProfile().getUserName();
        this.wishlistItems = wishlist.getWishlistItems().stream()
            .map(WishlistItemResponse::new)
            .collect(Collectors.toList());
        this.totalItems = wishlist.getTotalItems();
        this.createdAt = wishlist.getCreatedAt();
        this.updatedAt = wishlist.getUpdatedAt();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public List<WishlistItemResponse> getWishlistItems() { return wishlistItems; }
    public void setWishlistItems(List<WishlistItemResponse> wishlistItems) { this.wishlistItems = wishlistItems; }
    
    public Integer getTotalItems() { return totalItems; }
    public void setTotalItems(Integer totalItems) { this.totalItems = totalItems; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}