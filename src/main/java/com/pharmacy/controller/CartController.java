package com.pharmacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pharmacy.dto.CartRequest;
import com.pharmacy.dto.CartResponse;
import com.pharmacy.service.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    @GetMapping("/{userName}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CartResponse> getCart(@PathVariable String userName) {
        CartResponse cart = cartService.getCart(userName);
        return ResponseEntity.ok(cart);
    }
    
    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CartResponse> addToCart(@Valid @RequestBody CartRequest request) {
        CartResponse cart = cartService.addToCart(request);
        return ResponseEntity.ok(cart);
    }
    
    @PutMapping("/update/{cartItemId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CartResponse> updateCartItem(
            @PathVariable Long cartItemId, 
            @RequestParam Integer quantity) {
        CartResponse cart = cartService.updateCartItem(cartItemId, quantity);
        return ResponseEntity.ok(cart);
    }
    
    @DeleteMapping("/remove/{cartItemId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CartResponse> removeFromCart(@PathVariable Long cartItemId) {
        CartResponse cart = cartService.removeFromCart(cartItemId);
        return ResponseEntity.ok(cart);
    }
    
    @DeleteMapping("/clear/{userName}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> clearCart(@PathVariable String userName) {
        cartService.clearCart(userName);
        return ResponseEntity.ok("Cart cleared successfully");
    }
    
    @GetMapping("/count/{userName}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Integer> getCartItemCount(@PathVariable String userName) {
        Integer count = cartService.getCartItemCount(userName);
        return ResponseEntity.ok(count);
    }
    
    @PostMapping("/move-from-wishlist/{userName}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CartResponse> moveToCart(
            @PathVariable String userName, 
            @RequestParam Long wishlistItemId) {
        CartResponse cart = cartService.moveToCart(userName, wishlistItemId);
        return ResponseEntity.ok(cart);
    }
}