package com.pharmacy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharmacy.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.medicine.id = :medicineId")
    Optional<CartItem> findByCartIdAndMedicineId(@Param("cartId") Long cartId, @Param("medicineId") Long medicineId);
    
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.userProfile.userName = :userName")
    List<CartItem> findByUserName(@Param("userName") String userName);
    
    @Query("DELETE FROM CartItem ci WHERE ci.cart.userProfile.userName = :userName")
    void deleteByUserName(@Param("userName") String userName);
}