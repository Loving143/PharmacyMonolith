package com.user.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String batchNumber;
    private LocalDate manufacturingDate;
    private LocalDate expiryDate;
    private Integer availableTablet;
    private Integer availableBatch;
    private double pricePerTablet;
    private double pricePerBatch; 
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;
    
    public Long getId() {
        return id;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public LocalDate getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(LocalDate manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public double getPricePerTablet() {
        return pricePerTablet;
    }

    public void setPricePerTablet(double pricePerTablet) {
        this.pricePerTablet = pricePerTablet;
    }

    public double getPricePerBatch() {
        return pricePerBatch;
    }

    public void setPricePerBatch(double pricePerBatch) {
        this.pricePerBatch = pricePerBatch;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

	public Integer getAvailableTablet() {
		return availableTablet;
	}

	public void setAvailableTablet(Integer availableTablet) {
		this.availableTablet = availableTablet;
	}

	public Integer getAvailableBatch() {
		return availableBatch;
	}

	public void setAvailableBatch(Integer availableBatch) {
		this.availableBatch = availableBatch;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
