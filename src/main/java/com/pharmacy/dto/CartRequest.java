package com.pharmacy.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CartRequest {
    
    @NotNull(message = "Medicine ID is required")
    private Long medicineId;
    
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
    
    @NotNull(message = "Unit type is required")
    private String unitType; // "STRIP" or "TABLET"
    
    private String userName;
    
    // Constructors
    public CartRequest() {}
    
    public CartRequest(Long medicineId, Integer quantity, String unitType, String userName) {
        this.medicineId = medicineId;
        this.quantity = quantity;
        this.unitType = unitType;
        this.userName = userName;
    }
    
    // Getters and Setters
    public Long getMedicineId() { return medicineId; }
    public void setMedicineId(Long medicineId) { this.medicineId = medicineId; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public String getUnitType() { return unitType; }
    public void setUnitType(String unitType) { this.unitType = unitType; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
}