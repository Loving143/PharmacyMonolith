package com.user.response;

import java.util.List;
import java.util.stream.Collectors;

import com.user.master.entity.MedicineCategory;

public class CategoryResponse {
	
	private String name;
	
	private String categoryCode;
	
	private List<SubCategoryResponse>subCategoryList;

	public CategoryResponse(MedicineCategory cat) {
		this.name = cat.getName();
		this.categoryCode = cat.getCategoryCode();
		this.subCategoryList = cat.getSubCategories().stream().map(SubCategoryResponse::new).collect(Collectors.toList());
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public List<SubCategoryResponse> getSubCategoryList() {
		return subCategoryList;
	}

	public void setSubCategoryList(List<SubCategoryResponse> subCategoryList) {
		this.subCategoryList = subCategoryList;
	}
	
	
}
