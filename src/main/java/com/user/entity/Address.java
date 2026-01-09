package com.user.entity;

import java.time.LocalDateTime;

import com.user.dto.AddressRequest;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String mobile;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String house;
    private String area;
    private String landmark;
    
    private String pincode;

    private Double latitude;
    private Double longitude;

    private boolean isDefault;
    
    private String  userName;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public Address(AddressRequest request) {
    	this.name = request.getName();
    	this.mobile = request.getMobile();
    	this.house = request.getHouse();
    	this.area = request.getArea();
    	this.landmark = request.getLandmark();
    	this.addressLine1 = request.getHouse()+" "+request.getArea()+" "+request.getLandmark();
    	this.addressLine2 = request.getAddressLine2();
    	this.city = request.getCity();
    	this.state = request.getState();
    	this.pincode = request.getPincode();
    	this.longitude = request.getLatitude();
    	this.latitude = request.getLatitude();
    	this.isDefault = request.isDefault();
	}
	@PrePersist
    public void prePersist(){
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate(){
    updatedAt = LocalDateTime.now();
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getHouse() {
		return house;
	}
	public void setHouse(String house) {
		this.house = house;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getLandmark() {
		return landmark;
	}
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}
    
    
}
