package com.pharmacy.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pharmacy.dto.CartRequest;
import com.pharmacy.dto.CartResponse;
import com.pharmacy.entity.Cart;
import com.pharmacy.entity.CartItem;
import com.pharmacy.repository.CartItemRepository;
import com.pharmacy.repository.CartRepository;
import com.pharmacy.repository.WishlistItemRepository;
import com.pharmacy.service.CartService;
import com.user.entity.Medicine;
import com.user.entity.UserProfiles;
import com.user.repository.MedicineRepository;
import com.user.repository.UserProfileRepository;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private UserProfileRepository userProfileRepository;
    
    @Autowired
    private MedicineRepository medicineRepository;
    
    @Autowired
    private WishlistItemRepository wishlistItemRepository;
    
    @Override
    public CartResponse getCart(String userName) {
        Cart cart = getOrCreateCart(userName);
        return new CartResponse(cart);
    }
    
    @Override
    public CartResponse addToCart(CartRequest request) {
        Cart cart = getOrCreateCart(request.getUserName());
        Medicine medicine = medicineRepository.findById(request.getMedicineId())
            .orElseThrow(() -> new RuntimeException("Medicine not found"));
        
        // Check if item already exists in cart
        Optional<CartItem> existingItem = cartItemRepository
            .findByCartIdAndMedicineId(cart.getId(), request.getMedicineId());
        
        if (existingItem.isPresent()) {
            // Update quantity
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            cartItemRepository.save(item);
        } else {
            // Create new cart item
            Double unitPrice = "STRIP".equals(request.getUnitType()) 
                ? medicine.getSellingCostStrip() 
                : medicine.getSellingCostTablet();
            
            CartItem cartItem = new CartItem(cart, medicine, request.getQuantity(), unitPrice, request.getUnitType());
            cart.addItem(cartItem);
            cartItemRepository.save(cartItem);
        }
        
        cart.calculateTotals();
        cartRepository.save(cart);
        
        return new CartResponse(cart);
    }
    
    @Override
    public CartResponse updateCartItem(Long cartItemId, Integer quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new RuntimeException("Cart item not found"));
        
        if (quantity <= 0) {
            return removeFromCart(cartItemId);
        }
        
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        
        Cart cart = cartItem.getCart();
        cart.calculateTotals();
        cartRepository.save(cart);
        
        return new CartResponse(cart);
    }
    
    @Override
    public CartResponse removeFromCart(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new RuntimeException("Cart item not found"));
        
        Cart cart = cartItem.getCart();
        cart.removeItem(cartItem);
        cartItemRepository.delete(cartItem);
        cartRepository.save(cart);
        
        return new CartResponse(cart);
    }
    
    @Override
    public void clearCart(String userName) {
        cartItemRepository.deleteByUserName(userName);
        Optional<Cart> cart = cartRepository.findByUserName(userName);
        if (cart.isPresent()) {
            cart.get().getCartItems().clear();
            cart.get().calculateTotals();
            cartRepository.save(cart.get());
        }
    }
    
    @Override
    public CartResponse moveToCart(String userName, Long wishlistItemId) {
        // Implementation would move item from wishlist to cart
        // This is a placeholder - full implementation would require wishlist integration
        throw new RuntimeException("Move to cart functionality not yet implemented");
    }
    
    @Override
    public Integer getCartItemCount(String userName) {
        Optional<Cart> cart = cartRepository.findByUserName(userName);
        return cart.map(Cart::getTotalItems).orElse(0);
    }
    
    private Cart getOrCreateCart(String userName) {
        Optional<Cart> existingCart = cartRepository.findByUserNameWithItems(userName);
        
        if (existingCart.isPresent()) {
            return existingCart.get();
        }
        
        UserProfiles userProfile = userProfileRepository.findByUserName(userName)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Cart newCart = new Cart(userProfile);
        return cartRepository.save(newCart);
    }
}