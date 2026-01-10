package com.pharmacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pharmacy.dto.WishlistResponse;
import com.pharmacy.service.WishlistService;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
    
    @Autowired
    private WishlistService wishlistService;
    
    @GetMapping("/{userName}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<WishlistResponse> getWishlist(@PathVariable String userName) {
        WishlistResponse wishlist = wishlistService.getWishlist(userName);
        return ResponseEntity.ok(wishlist);
    }
    
    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<WishlistResponse> addToWishlist(
            @RequestParam String userName,
            @RequestParam Long medicineId) {
        WishlistResponse wishlist = wishlistService.addToWishlist(userName, medicineId);
        return ResponseEntity.ok(wishlist);
    }
    
    @DeleteMapping("/remove")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<WishlistResponse> removeFromWishlist(
            @RequestParam String userName,
            @RequestParam Long medicineId) {
        WishlistResponse wishlist = wishlistService.removeFromWishlist(userName, medicineId);
        return ResponseEntity.ok(wishlist);
    }
    
    @DeleteMapping("/clear/{userName}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> clearWishlist(@PathVariable String userName) {
        wishlistService.clearWishlist(userName);
        return ResponseEntity.ok("Wishlist cleared successfully");
    }
    
    @GetMapping("/check")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Boolean> isInWishlist(
            @RequestParam String userName,
            @RequestParam Long medicineId) {
        boolean isInWishlist = wishlistService.isInWishlist(userName, medicineId);
        return ResponseEntity.ok(isInWishlist);
    }
    
    @GetMapping("/count/{userName}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Integer> getWishlistItemCount(@PathVariable String userName) {
        Integer count = wishlistService.getWishlistItemCount(userName);
        return ResponseEntity.ok(count);
    }
}