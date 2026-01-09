package com.user.request;

import java.time.LocalDate;

public class UserProfileRequest {
	
	    private String firstName;
	    
	    private String lastName;
	    
	    private LocalDate dateOfBirth;
	    
	    private String phoneNumber;
	    
	    private String bloodGroup;
	    
	    private String profilePicture;
	    
	    private String healthCondition;
	    
	    private Integer age;

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

		public Integer getAge() {
			return age;
		}

		public void setAge(Integer age) {
			this.age = age;
		}

		public String getHealthCondition() {
			return healthCondition;
		}

		public void setHealthCondition(String healthCondition) {
			this.healthCondition = healthCondition;
		}
	    
	    
	}
