package com.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.master.entity.MedicineCategory;

public interface MedicineCategoryRepository extends JpaRepository<MedicineCategory, Integer>{

	Optional<MedicineCategory> findByCategoryCode(String categoryCode);

}
