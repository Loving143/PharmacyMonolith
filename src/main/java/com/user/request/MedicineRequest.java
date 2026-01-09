package com.user.request;

import java.time.LocalDate;

import com.user.MedicineStatus;


public class MedicineRequest {

	 private String name;
	 private String medicineCode;
	 private String brandName;
	 private String manufacturer; 
	 private String description;  
	 private String composition;      
	 private Double discount;
	 private LocalDate manufactureDate;
	 private LocalDate expiryDate;   
	 private String imageUrl;       
	 private String batchNumber;  
	 private String supplierName;  
	 private String supplierInvoiceNo; 
	 private String subCategoryCode;
	 private Integer stripQuantity;
	 private Double purchaseCostStrip;
	 private Double sellingCostStrip;
	 private MedicineStatus medicineStatus;
	 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getComposition() {
		return composition;
	}
	public void setComposition(String composition) {
		this.composition = composition;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public LocalDate getManufactureDate() {
		return manufactureDate;
	}
	public void setManufactureDate(LocalDate manufactureDate) {
		this.manufactureDate = manufactureDate;
	}
	public LocalDate getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getSupplierInvoiceNo() {
		return supplierInvoiceNo;
	}
	public void setSupplierInvoiceNo(String supplierInvoiceNo) {
		this.supplierInvoiceNo = supplierInvoiceNo;
	}
	
	public String getMedicineCode() {
		return medicineCode;
	}
	public void setMedicineCode(String medicineCode) {
		this.medicineCode = medicineCode;
	}
	public String getSubCategoryCode() {
		return subCategoryCode;
	}
	public void setSubCategoryCode(String subCategoryCode) {
		this.subCategoryCode = subCategoryCode;
	}
	public Integer getStripQuantity() {
		return stripQuantity;
	}
	public void setStripQuantity(Integer stripQuantity) {
		this.stripQuantity = stripQuantity;
	}
	public Double getPurchaseCostStrip() {
		return purchaseCostStrip;
	}
	public void setPurchaseCostStrip(Double purchaseCostStrip) {
		this.purchaseCostStrip = purchaseCostStrip;
	}
	public Double getSellingCostStrip() {
		return sellingCostStrip;
	}
	public void setSellingCostStrip(Double sellingCostStrip) {
		this.sellingCostStrip = sellingCostStrip;
	}
	public MedicineStatus getMedicineStatus() {
		return medicineStatus;
	}
	public void setMedicineStatus(MedicineStatus medicineStatus) {
		this.medicineStatus = medicineStatus;
	}
     
     
     
}
