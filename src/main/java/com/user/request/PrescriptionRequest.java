package com.user.request;

import java.util.List;

public class PrescriptionRequest {
	 private String userName;
     private String prescribedBy;
     private String status;
     private List<MedicineRequest> medicines;
     
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPrescribedBy() {
		return prescribedBy;
	}
	public void setPrescribedBy(String prescribedBy) {
		this.prescribedBy = prescribedBy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<MedicineRequest> getMedicines() {
		return medicines;
	}
	public void setMedicines(List<MedicineRequest> medicines) {
		this.medicines = medicines;
	}
    
}
