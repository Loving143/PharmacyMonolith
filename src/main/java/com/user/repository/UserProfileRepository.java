package com.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.user.entity.UserProfiles;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfiles, Long> {
    
    Optional<UserProfiles> findByUserName(String userName);
    
    @Query("SELECT up FROM UserProfiles up WHERE up.bloodGroup = :bloodGroup")
    List<UserProfiles> findByBloodGroup(@Param("bloodGroup") String bloodGroup);
    
    @Query("SELECT up FROM UserProfiles up WHERE up.firstName LIKE %:name% OR up.lastName LIKE %:name%")
    List<UserProfiles> findByNameContaining(@Param("name") String name);
    
    @Query("SELECT COUNT(up) FROM UserProfiles up WHERE up.bloodGroup = :bloodGroup")
    Long countByBloodGroup(@Param("bloodGroup") String bloodGroup);
    
    boolean existsByUserName(String userName);
}