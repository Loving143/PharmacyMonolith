package com.user.response;

import com.user.entity.Medicine;

public class MedicineResponse {

	 private Long id;
	 private String name;
	 private String medicineCode;
	 private String dosage;
	 private String frequency;
	 private String notes;
	 private String imageUrl;
	 private Double price;
	 private Double discount;
	 private Integer stockQuantity; 
	 
	public MedicineResponse(Medicine med) {
		this.id = med.getId();
		this.name = med.getName();
		this.notes = med.getNotes();
		this.medicineCode = med.getMedicineCode();
		this.imageUrl = med.getImageUrl();
		this.discount = med.getDiscountPercentage();
		this.price = med.getSellingCostStrip();
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
	public String getDosage() {
		return dosage;
	}
	public void setDosage(String dosage) {
		this.dosage = dosage;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getMedicineCode() {
		return medicineCode;
	}
	public void setMedicineCode(String medicineCode) {
		this.medicineCode = medicineCode;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Integer getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	 
	 
}
