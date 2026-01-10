package com.pharmacy.service;

import com.pharmacy.dto.CartRequest;
import com.pharmacy.dto.CartResponse;

public interface CartService {
    
    CartResponse getCart(String userName);
    
    CartResponse addToCart(CartRequest request);
    
    CartResponse updateCartItem(Long cartItemId, Integer quantity);
    
    CartResponse removeFromCart(Long cartItemId);
    
    void clearCart(String userName);
    
    CartResponse moveToCart(String userName, Long wishlistItemId);
    
    Integer getCartItemCount(String userName);
}