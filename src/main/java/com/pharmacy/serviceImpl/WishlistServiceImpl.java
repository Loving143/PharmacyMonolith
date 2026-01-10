package com.pharmacy.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pharmacy.dto.WishlistResponse;
import com.pharmacy.entity.Wishlist;
import com.pharmacy.entity.WishlistItem;
import com.pharmacy.repository.WishlistItemRepository;
import com.pharmacy.repository.WishlistRepository;
import com.pharmacy.service.WishlistService;
import com.user.entity.Medicine;
import com.user.entity.UserProfiles;
import com.user.repository.MedicineRepository;
import com.user.repository.UserProfileRepository;

@Service
@Transactional
public class WishlistServiceImpl implements WishlistService {
    
    @Autowired
    private WishlistRepository wishlistRepository;
    
    @Autowired
    private WishlistItemRepository wishlistItemRepository;
    
    @Autowired
    private UserProfileRepository userProfileRepository;
    
    @Autowired
    private MedicineRepository medicineRepository;
    
    @Override
    public WishlistResponse getWishlist(String userName) {
        Wishlist wishlist = getOrCreateWishlist(userName);
        return new WishlistResponse(wishlist);
    }
    
    @Override
    public WishlistResponse addToWishlist(String userName, Long medicineId) {
        Wishlist wishlist = getOrCreateWishlist(userName);
        Medicine medicine = medicineRepository.findById(medicineId)
            .orElseThrow(() -> new RuntimeException("Medicine not found"));
        
        // Check if item already exists in wishlist
        Optional<WishlistItem> existingItem = wishlistItemRepository
            .findByWishlistIdAndMedicineId(wishlist.getId(), medicineId);
        
        if (existingItem.isPresent()) {
            throw new RuntimeException("Medicine already in wishlist");
        }
        
        WishlistItem wishlistItem = new WishlistItem(wishlist, medicine);
        wishlist.addItem(wishlistItem);
        wishlistItemRepository.save(wishlistItem);
        wishlistRepository.save(wishlist);
        
        return new WishlistResponse(wishlist);
    }
    
    @Override
    public WishlistResponse removeFromWishlist(String userName, Long medicineId) {
        Wishlist wishlist = wishlistRepository.findByUserNameWithItems(userName)
            .orElseThrow(() -> new RuntimeException("Wishlist not found"));
        
        WishlistItem wishlistItem = wishlistItemRepository
            .findByUserNameAndMedicineId(userName, medicineId)
            .orElseThrow(() -> new RuntimeException("Item not found in wishlist"));
        
        wishlist.removeItem(wishlistItem);
        wishlistItemRepository.delete(wishlistItem);
        wishlistRepository.save(wishlist);
        
        return new WishlistResponse(wishlist);
    }
    
    @Override
    public void clearWishlist(String userName) {
        Optional<Wishlist> wishlist = wishlistRepository.findByUserNameWithItems(userName);
        if (wishlist.isPresent()) {
            wishlist.get().getWishlistItems().clear();
            wishlist.get().setTotalItems(0);
            wishlistRepository.save(wishlist.get());
        }
    }
    
    @Override
    public boolean isInWishlist(String userName, Long medicineId) {
        return wishlistItemRepository.findByUserNameAndMedicineId(userName, medicineId).isPresent();
    }
    
    @Override
    public Integer getWishlistItemCount(String userName) {
        Optional<Wishlist> wishlist = wishlistRepository.findByUserName(userName);
        return wishlist.map(Wishlist::getTotalItems).orElse(0);
    }
    
    private Wishlist getOrCreateWishlist(String userName) {
        Optional<Wishlist> existingWishlist = wishlistRepository.findByUserNameWithItems(userName);
        
        if (existingWishlist.isPresent()) {
            return existingWishlist.get();
        }
        
        UserProfiles userProfile = userProfileRepository.findByUserName(userName)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Wishlist newWishlist = new Wishlist(userProfile);
        return wishlistRepository.save(newWishlist);
    }
}