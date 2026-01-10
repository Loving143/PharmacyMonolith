package com.pharmacy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharmacy.entity.WishlistItem;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    
    @Query("SELECT wi FROM WishlistItem wi WHERE wi.wishlist.id = :wishlistId AND wi.medicine.id = :medicineId")
    Optional<WishlistItem> findByWishlistIdAndMedicineId(@Param("wishlistId") Long wishlistId, @Param("medicineId") Long medicineId);
    
    @Query("SELECT wi FROM WishlistItem wi WHERE wi.wishlist.userProfile.userName = :userName AND wi.medicine.id = :medicineId")
    Optional<WishlistItem> findByUserNameAndMedicineId(@Param("userName") String userName, @Param("medicineId") Long medicineId);
}