package com.pharmacy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharmacy.entity.Wishlist;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    
    @Query("SELECT w FROM Wishlist w WHERE w.userProfile.userName = :userName")
    Optional<Wishlist> findByUserName(@Param("userName") String userName);
    
    @Query("SELECT w FROM Wishlist w LEFT JOIN FETCH w.wishlistItems wi LEFT JOIN FETCH wi.medicine WHERE w.userProfile.userName = :userName")
    Optional<Wishlist> findByUserNameWithItems(@Param("userName") String userName);
}