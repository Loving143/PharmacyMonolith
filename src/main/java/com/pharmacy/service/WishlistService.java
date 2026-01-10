package com.pharmacy.service;

import com.pharmacy.dto.WishlistResponse;

public interface WishlistService {
    
    WishlistResponse getWishlist(String userName);
    
    WishlistResponse addToWishlist(String userName, Long medicineId);
    
    WishlistResponse removeFromWishlist(String userName, Long medicineId);
    
    void clearWishlist(String userName);
    
    boolean isInWishlist(String userName, Long medicineId);
    
    Integer getWishlistItemCount(String userName);
}