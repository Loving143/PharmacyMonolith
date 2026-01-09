package com.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.entity.UserProfiles;

@Repository
public interface UserRepository extends JpaRepository<UserProfiles, Long>{


	List<UserProfiles> findByBloodGroup(String bloodGroup);

	boolean existsByUserName(String userName);

	Optional<UserProfiles> findByUserName(String userName);

}
