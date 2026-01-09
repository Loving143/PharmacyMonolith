package com.user.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.user.request.PrescriptionRequest;
import com.user.response.PrescriptionResponse;

public interface PrescriptionService {

    PrescriptionResponse createPrescription(PrescriptionRequest request,Integer prescriptionId);

    PrescriptionResponse updatePrescription(Long id, PrescriptionRequest request);

    void deletePrescription(Long id);

    PrescriptionResponse getPrescriptionById(Long id);

    List<PrescriptionResponse> getPrescriptionsByUserName(String userName);

    List<PrescriptionResponse> getAllPrescriptions();

	void uploadPrescriptions(MultipartFile[] files, String userName);

	ResponseEntity<byte[]> downloadFile(Long fileId);
}
