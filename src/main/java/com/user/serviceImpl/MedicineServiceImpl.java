package com.user.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.user.MedicineStatus;
import com.user.entity.Medicine;
import com.user.master.entity.MedicineSubCategory;
import com.user.repository.MedicineCategoryRepository;
import com.user.repository.MedicineRepository;
import com.user.repository.MedicineSubCategoryRepository;
import com.user.request.MedicineRequest;
import com.user.response.CategoryResponse;
import com.user.response.MedicineResponse;
import com.user.service.MedicineService;

@Service
public class MedicineServiceImpl implements MedicineService{

	@Autowired
	private MedicineRepository medicineRepository;
	
	@Autowired
	private MedicineCategoryRepository catgeoryRepo;
	
	@Autowired
	private MedicineSubCategoryRepository subcategoryRepository;
	@Override
	public MedicineResponse fetchMedicineByMedicineCode(String medicineCode) {
		System.out.println("Medicine Code: "+medicineCode);
		Medicine medicine = medicineRepository.fetchByMedicineCode(medicineCode,MedicineStatus.ACTIVE).
				orElseThrow(()-> new RuntimeException("Medicine with MedicineCode does not exists!!"));
		MedicineResponse res= new MedicineResponse(medicine);
		return res;
	}
	@Override
//	@CacheEvict(value = "searchResults", allEntries = true)
	public void addMedicine(MedicineRequest req) {
		Medicine medicine = null;
		MedicineSubCategory subcategory = subcategoryRepository.findBySubCategoryCode(req.getSubCategoryCode())
		.orElseThrow(()-> new RuntimeException("Medicine Subcategory not found!"));
		
		if(medicineRepository.existsByMedicineCodeAndBatchNumber(req.getMedicineCode(),req.getBatchNumber())) {
			medicine =	medicineRepository.findMedicineByMedicineCodeAndBatchNumber(req.getMedicineCode(),req.getBatchNumber());
			int totalStripQuantity = medicine.getAvailableStrip() +req.getStripQuantity();
			medicine.setAvailableStrip(totalStripQuantity);
		}else {
			if(medicineRepository.existsByMedicineCodeAndExpiryDate(req.getMedicineCode(),req.getExpiryDate()))
					throw new RuntimeException("Same medicine having different batches can not have same expiry date!");
				medicine = new Medicine(req);
				medicine.setSubCategory(subcategory);
		}
		medicineRepository.save(medicine);
	}
	
	@Override
	public List<MedicineResponse> getAll() {
		return medicineRepository.findAll().stream().map(MedicineResponse::new ).collect(Collectors.toList());
	}

    // ✅ Get by Code + Batch
    public Medicine getByCodeAndBatch(String code, String batch) {
        return  medicineRepository.findByMedicineCodeAndBatchNumber(code, batch)
                .orElseThrow(() -> new RuntimeException("Medicine not found for code: " + code + " and batch: " + batch));
        
    }

    // ✅ Update by Code + Batch
    @CacheEvict(value = "searchResults", allEntries = true)
    public Medicine updateByCodeAndBatch(String code, String batch,MedicineRequest req) {
//        validate(req);
    	Medicine med = getByCodeAndBatch(code, batch);
        copyRequestToEntity(req, med);
        med.setUpdatedAt(LocalDateTime.now());
        return medicineRepository.save(med);
    }

    // ✅ Delete by Code + Batch

    @CacheEvict(value = "searchResults", key = "#code.trim().toLowerCase()")
    public void deleteByCodeAndBatch(String code, String batch) {
        if (!medicineRepository.existsByMedicineCodeAndBatchNumber(code, batch)) {
            throw new RuntimeException("Medicine not found for code: " + code + " and batch: " + batch);
        }
        medicineRepository.deleteByMedicineCodeAndBatchNumber(code, batch);
    }

    // Helper: copy request to entity
    private void copyRequestToEntity(MedicineRequest req, Medicine med) {
        med.setMedicineCode(req.getMedicineCode());
        med.setBatchNumber(req.getBatchNumber());
        med.setName(req.getName());
        med.setBrandName(req.getBrandName());
        med.setManufacturer(req.getManufacturer());
        med.setDescription(req.getDescription());
        med.setComposition(req.getComposition());
        med.setDiscount(req.getDiscount());
        med.setPurchaseCostStrip(req.getPurchaseCostStrip());
        med.setSellingCostStrip(req.getSellingCostStrip());
        med.setManufactureDate(req.getManufactureDate());
        med.setExpiryDate(req.getExpiryDate());
        med.setSupplierName(req.getSupplierName());
        med.setSupplierInvoiceNo(req.getSupplierInvoiceNo());
        med.setImageUrl(req.getImageUrl());
    }
    
    @Override
    public List<Medicine> findByBrandName(String brandName) {
        List<Medicine> list = medicineRepository.findByBrandNameIgnoreCase(brandName);
        if (list.isEmpty()) {
            throw new RuntimeException("No medicines found for brand: " + brandName);
        }
        return list;
    }

    @Override
    public List<Medicine> findByManufacturer(String manufacturer) {
        List<Medicine> list = medicineRepository.findByManufacturerIgnoreCase(manufacturer);
        if (list.isEmpty()) {
            throw new RuntimeException("No medicines found for manufacturer: " + manufacturer);
        }
        return list;
    }

    @Override
    public List<MedicineResponse> searchMedicines(String keyword) {
        List<MedicineResponse> list = medicineRepository.searchMedicines(keyword,MedicineStatus.ACTIVE).stream().map(MedicineResponse::new ).collect(Collectors.toList());
        if (list.isEmpty()) {
            throw new RuntimeException("No medicines found matching: " + keyword);
        }
        return list;
    }

    @Override
    public List<Medicine> findAllActiveMedicines() {
        List<Medicine> list = medicineRepository.findAllActiveMedicines(MedicineStatus.ACTIVE);
        if (list.isEmpty()) {
            throw new RuntimeException("No active medicines found in inventory");
        }
        return list;
    }

    @Override
    public List<Medicine> findExpiredMedicines() {
        List<Medicine> list = medicineRepository.findExpiredMedicines(MedicineStatus.EXPIRED);
        if (list.isEmpty()) {
            throw new RuntimeException("No expired medicines found");
        }
        return list;
    }
	@Override
//	@Cacheable(
//		    value = "searchResults",
//		    key = "#keyword.trim().toLowerCase()",
//		    unless = "#result == null or #result.isEmpty()"
//		)
	public List<MedicineResponse> searchMedicinesFullText(String keyword) {
		return medicineRepository.searchMedicinesFullText(keyword).
				stream().map(MedicineResponse::new ).collect(Collectors.toList());
	}
	
	 @Cacheable(value = "medicineCache", key = "#name.trim().toLowerCase()")
    public List<Medicine> getByName(String name) {
        return medicineRepository.findByName(name);
    }

    // Search by medicine code
	 @Cacheable(value = "medicineCache", key = "#code.trim().toLowerCase()")
    public Medicine getByCode(String code) {
        return medicineRepository.findByMedicineCode(code)
                .orElseThrow(() -> new RuntimeException("Medicine not found with code: " + code));
    }

    // Search by brand name
	@Cacheable(value = "medicineCache", key = "#brandName.trim().toLowerCase()")
	public List<Medicine> getByBrandName(String brandName) {
        return medicineRepository.findByBrandName(brandName);
    }

    // Search by manufacturer
	@Cacheable(value = "medicineCache", key = "#manufacturer.trim().toLowerCase()")
    public List<Medicine> getByManufacturer(String manufacturer) {
        return medicineRepository.findByManufacturer(manufacturer);
    }
	@Override
	public List<CategoryResponse> getAllCategories() {
		return catgeoryRepo.findAll().stream().map(CategoryResponse::new).collect(Collectors.toList());
	}
	@Override
	public List<MedicineSubCategory> getAllSubCategories() {
		return subcategoryRepository.findAll();
	}
	@Override
	public List<MedicineResponse> fetchMedicineBySubCatCode(String subCatCode) {
		return medicineRepository.fetchMedicineBySubCatCode(subCatCode)
				.stream().map(MedicineResponse::new).collect(Collectors.toList());
	}


}
