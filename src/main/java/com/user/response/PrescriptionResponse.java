package com.user.response;

import java.time.Instant;
import java.util.List;

public class PrescriptionResponse {
	
	private Long id;
    private String prescribedBy;
    private String status;
    private Instant createdAt;
    private List<MedicineResponse> medicines;
    private List<FileResponse> files;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Instant getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
	public List<MedicineResponse> getMedicines() {
		return medicines;
	}
	public void setMedicines(List<MedicineResponse> medicines) {
		this.medicines = medicines;
	}
	public List<FileResponse> getFiles() {
		return files;
	}
	public void setFiles(List<FileResponse> files) {
		this.files = files;
	}
    
    
    
    
}
