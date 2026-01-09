package com.user.response;

import com.user.master.entity.MedicineSubCategory;

public class SubCategoryResponse {
	private Long id;
	private String name;
	private String subCategoryCode;
	
	public SubCategoryResponse(MedicineSubCategory subCat) {
		this.id = subCat.getId();
		this.name = subCat.getName();
		this.subCategoryCode = subCat.getSubCategoryCode();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubCategoryCode() {
		return subCategoryCode;
	}
	public void setSubCategoryCode(String subCategoryCode) {
		this.subCategoryCode = subCategoryCode;
	}
	

}
