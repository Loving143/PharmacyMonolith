package com.user.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.user.MedicineStatus;
import com.user.master.entity.MedicineSubCategory;
import com.user.request.MedicineRequest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
	    name = "medicines",
	    indexes = {
	    	@Index(name = "idx_medicine_name", columnList = "name"),  
	        @Index(name = "idx_medicine_code", columnList = "medicineCode"),   
	        @Index(name = "idx_medicine_brand_name", columnList = "brandName"),
	        @Index(name = "idx_medicine_manufacturer", columnList = "manufacturer"),
	        @Index(name = "idx_medicine_price", columnList = "price")
	    },
	    uniqueConstraints = {
	        @UniqueConstraint(name = "uk_medicine_code", columnNames = "medicineCode")
	    }
	)
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String medicineCode;
    
    private String name;
    private String brandName;                
    private String manufacturer;               
    private String description;    
    private String composition;      
    private Double discount; 
    private Integer availableStrip;
    private Integer availableTablet;
    private Double purchaseCostStrip;
    private Double sellingCostStrip;
    private Double purchaseCostTablet;
    private Double sellingCostTablet;
    private LocalDate manufactureDate;
    private LocalDate expiryDate;   
    private String imageUrl;       
    private String batchNumber;  
    private String supplierName;  
    private String supplierInvoiceNo; 
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Column(length = 1000)
    private String notes;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;
    @ManyToOne
    @JoinColumn(name = "sub_category_id", nullable = false)
    private MedicineSubCategory subCategory; 
    
    @OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Batch> batches;

    @Enumerated(EnumType.STRING)
    private MedicineStatus medicineStatus;
    
    public Medicine(MedicineRequest req) {
		this.name = req.getName();
		this.brandName = req.getBrandName();
		this.batchNumber = req.getBatchNumber();
		this.manufactureDate = req.getManufactureDate();
		this.manufacturer = req.getManufacturer();
		this.composition = req.getComposition();
		this.description = req.getDescription();
		this.composition = req.getComposition();
		this.availableStrip = req.getStripQuantity();
		this.purchaseCostStrip = req.getPurchaseCostStrip();
		this.expiryDate = req.getExpiryDate();
		this.batchNumber = req.getBatchNumber();
		this.supplierInvoiceNo = req.getSupplierInvoiceNo();
		this.supplierName = req.getSupplierName();
		this.medicineCode = req.getMedicineCode();
		this.createdAt = LocalDateTime.now();
		this.updatedAt =LocalDateTime.now();
		this.medicineStatus = MedicineStatus.AVAILABLE;
	}
    
    public String getMedicineCode() {
		return medicineCode;
	}

	public void setMedicineCode(String medicineCode) {
		this.medicineCode = medicineCode;
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

	public Double getDiscountPercentage() {
		return discount;
	}

	public void setDiscountPercentage(Double discountPercentage) {
		this.discount = discountPercentage;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Prescription getPrescription() {
		return prescription;
	}

	public void setPrescription(Prescription prescription) {
		this.prescription = prescription;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Integer getAvailableStrip() {
		return availableStrip;
	}

	public void setAvailableStrip(Integer availableStrip) {
		this.availableStrip = availableStrip;
	}

	public Integer getAvailableTablet() {
		return availableTablet;
	}

	public void setAvailableTablet(Integer availableTablet) {
		this.availableTablet = availableTablet;
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

	public Double getPurchaseCostTablet() {
		return purchaseCostTablet;
	}

	public void setPurchaseCostTablet(Double purchaseCostTablet) {
		this.purchaseCostTablet = purchaseCostTablet;
	}

	public Double getSellingCostTablet() {
		return sellingCostTablet;
	}

	public void setSellingCostTablet(Double sellingCostTablet) {
		this.sellingCostTablet = sellingCostTablet;
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
	public MedicineSubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(MedicineSubCategory subCategory) {
		this.subCategory = subCategory;
	}
	public List<Batch> getBatches() {
		return batches;
	}

	public void setBatches(List<Batch> batches) {
		this.batches = batches;
	}
	public MedicineStatus getStatus() {
		return medicineStatus;
	}

	public void setStatus(MedicineStatus status) {
		this.medicineStatus = status;
	}

	public Medicine() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}
