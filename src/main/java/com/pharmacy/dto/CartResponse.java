package com.pharmacy.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.pharmacy.entity.Cart;

public class CartResponse {
    
    private Long id;
    private String userName;
    private List<CartItemResponse> cartItems;
    private Double totalAmount;
    private Integer totalItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public CartResponse() {}
    
    public CartResponse(Cart cart) {
        this.id = cart.getId();
        this.userName = cart.getUserProfile().getUserName();
        this.cartItems = cart.getCartItems().stream()
            .map(CartItemResponse::new)
            .collect(Collectors.toList());
        this.totalAmount = cart.getTotalAmount();
        this.totalItems = cart.getTotalItems();
        this.createdAt = cart.getCreatedAt();
        this.updatedAt = cart.getUpdatedAt();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public List<CartItemResponse> getCartItems() { return cartItems; }
    public void setCartItems(List<CartItemResponse> cartItems) { this.cartItems = cartItems; }
    
    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    
    public Integer getTotalItems() { return totalItems; }
    public void setTotalItems(Integer totalItems) { this.totalItems = totalItems; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}