package com.user.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import com.user.entity.UserProfiles;

public class UserProfileResponse {
	
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String bloodGroup;
    private String profilePicture;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer age;
    
    public UserProfileResponse(UserProfiles userProfiles) {
    	this.id = userProfiles.getId();
    	this.userName = userProfiles.getUserName();
    	this.firstName = userProfiles.getFirstName();
    	this.lastName = userProfiles.getLastName();
    	this.dateOfBirth = userProfiles.getDateOfBirth();
    	this.phoneNumber = userProfiles.getPhoneNumber();
    	this.bloodGroup = userProfiles.getBloodGroup();
    	this.profilePicture = userProfiles.getProfilePicture();
    	this.createdAt = userProfiles.getCreatedAt();
    	this.updatedAt = userProfiles.getUpdatedAt();
    	this.age = userProfiles.getAge();
	}

	public Integer getAge() {
        return age;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	
    
    
}