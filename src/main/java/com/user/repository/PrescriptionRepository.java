package com.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.entity.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long>{

	List<Prescription> findByUserProfileUserName(String userName);

}
