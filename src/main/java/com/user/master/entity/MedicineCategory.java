package com.user.master.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class MedicineCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    private String name;
    
    @Column(unique = true, nullable = false)
    private String categoryCode;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicineSubCategory> subCategories = new ArrayList<>();
    
    public void addSubCategory(MedicineSubCategory subCategory) {
        subCategories.add(subCategory);
        subCategory.setCategory(this);
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

	public List<MedicineSubCategory> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<MedicineSubCategory> subCategories) {
		this.subCategories = subCategories;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
    
    
}

