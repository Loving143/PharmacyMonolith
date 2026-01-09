package com.user.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.master.entity.MedicineCategory;
import com.user.master.entity.MedicineSubCategory;
import com.user.repository.MedicineCategoryRepository;
import com.user.repository.MedicineSubCategoryRepository;
import com.user.service.CategoryService;

import jakarta.ws.rs.BadRequestException;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
    private MedicineCategoryRepository categoryRepo;

    @Autowired
    private MedicineSubCategoryRepository subCategoryRepo;
    
    public MedicineCategory addCategory(String name, String categoryCode) {
    	Optional<MedicineCategory> medicineCategoryOpt = categoryRepo.findByCategoryCode(categoryCode);
        if(medicineCategoryOpt.isPresent())
        	throw new BadRequestException("Medicine Category already exists!");
    	MedicineCategory medicineCategory = new MedicineCategory();
    	medicineCategory.setCategoryCode(categoryCode);
    	medicineCategory.setName(name);
        return categoryRepo.save(medicineCategory);
    }

	    // Add Subcategory
	    public MedicineSubCategory addSubCategory(String name, String subCategoryCode,String categoryCode) {
	        MedicineCategory category = categoryRepo.findByCategoryCode(categoryCode)
	                .orElseThrow(() -> new RuntimeException("Category not found"));

	        if (subCategoryRepo.findBySubCategoryCode(subCategoryCode).isPresent()) {
	            throw new RuntimeException("Subcategory code already exists");
	        }

	        MedicineSubCategory subCategory = new MedicineSubCategory();
	        subCategory.setSubCategoryCode(subCategoryCode);
	        subCategory.setName(name);
	        category.addSubCategory(subCategory);
	        categoryRepo.save(category); 
	        return subCategory;
	    }
	    
	    public List<MedicineCategory> getAllCategories() {
	        return categoryRepo.findAll();
	    }
	

}
