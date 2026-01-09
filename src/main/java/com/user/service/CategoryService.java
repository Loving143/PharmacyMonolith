package com.user.service;

import java.util.List;

import com.user.master.entity.MedicineCategory;
import com.user.master.entity.MedicineSubCategory;

public interface CategoryService {

	MedicineCategory addCategory(String categoryCode, String categoryName);

	MedicineSubCategory addSubCategory(String categoryCode, String subCategoryName, String categoryCode2);

	List<MedicineCategory> getAllCategories();
}
