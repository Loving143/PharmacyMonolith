package com.user.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.user.MedicineStatus;
import com.user.entity.Medicine;
import com.user.response.MedicineResponse;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

	@Query("Select med from Medicine med "
			+ " where med.medicineCode =:medicineCode "
			+ " AND med.medicineStatus = :status")
	Optional<Medicine> fetchByMedicineCode(String medicineCode,MedicineStatus status);

	Medicine findMedicineByMedicineCodeAndBatchNumber(String medicineCode, String batchNumber);

	boolean existsByMedicineCodeAndBatchNumber(String medicineCode, String batchNumber);

	boolean existsByMedicineCodeAndExpiryDate(String medicineCode, LocalDate expiryDate);

	Optional<Medicine> findByMedicineCodeAndBatchNumber(String code, String batch);

	void deleteByMedicineCodeAndBatchNumber(String code, String batch);
	
	// 1. Find by Brand Name (case-insensitive)
    List<Medicine> findByBrandNameIgnoreCase(String brandName);

    // 2. Find by Manufacturer
    List<Medicine> findByManufacturerIgnoreCase(String manufacturer);

    // 3. Search by keyword in name, brand, or composition
    @Query("""
        SELECT m FROM Medicine m 
        WHERE (LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(m.brandName) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(m.composition) LIKE LOWER(CONCAT('%', :keyword, '%'))
           AND 
           m.medicineStatus = :status)
    """)
    List<Medicine> searchMedicines(String keyword,MedicineStatus status);
    
    @Query("""
        SELECT m FROM Medicine m
        WHERE m.medicineStatus =:status
    """)
    List<Medicine> findAllActiveMedicines(MedicineStatus status);

    // 5. Expired medicines
    @Query("""
        SELECT m FROM Medicine m
        where m.medicineStatus =:status
    """)
    List<Medicine> findExpiredMedicines(MedicineStatus status);

        @Query(value = """
            SELECT *, ts_rank(fulltext_vector, plainto_tsquery(:query)) AS rank
            FROM medicines
            WHERE fulltext_vector @@ plainto_tsquery(:query)
            ORDER BY rank DESC
            """, nativeQuery = true)
        List<Medicine> searchMedicinesFullText(@Param("query") String query);

		List<Medicine> findByName(String name);

		List<Medicine> findByBrandName(String brandName);

		List<Medicine> findByManufacturer(String manufacturer);

		Optional<Medicine> findByMedicineCode(String code);

		@Query("Select med "
				+ " from Medicine med "
				+ " inner join med.subCategory subCat"
				+ " where subCat.subCategoryCode =:subCatCode")
		List<Medicine> fetchMedicineBySubCatCode(String subCatCode);
    


}
