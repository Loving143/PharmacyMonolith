package com.user.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.user.entity.Medicine;
import com.user.entity.Prescription;
import com.user.entity.PrescriptionFile;
import com.user.entity.UserProfiles;
import com.user.repository.PrescriptionFileRepository;
import com.user.repository.PrescriptionRepository;
import com.user.repository.UserRepository;
import com.user.request.MedicineRequest;
import com.user.request.PrescriptionRequest;
import com.user.response.MedicineResponse;
import com.user.response.PrescriptionResponse;
import com.user.service.PrescriptionService;

import jakarta.ws.rs.BadRequestException;

@Service
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final UserRepository userProfileRepository;
    private final PrescriptionFileRepository fileRepository;

    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository,
                                   UserRepository userProfileRepository, PrescriptionFileRepository fileRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.userProfileRepository = userProfileRepository;
		this.fileRepository = fileRepository;
    }

    @Override
    public PrescriptionResponse createPrescription(PrescriptionRequest request,Integer prescriptionId) {
    	Prescription prescription = prescriptionRepository.findById(null).orElseThrow(()-> new BadRequestException("Prescription not found for this id"));
        UserProfiles user = userProfileRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new RuntimeException("User not found with username: " + request.getUserName()));
        List<Medicine>medicines = new ArrayList();
        
        for(MedicineRequest req:request.getMedicines()) {
        	Medicine medicine = new Medicine(req);
        	medicines.add(medicine);
        }
        prescription.setPrescribedBy(request.getPrescribedBy());
        prescription.setMedicines(medicines);
        prescription.setStatus(request.getStatus());
        prescription.setUserProfile(user);

        Prescription saved = prescriptionRepository.save(prescription);
        return mapToResponse(saved);
    }

    @Override
    public PrescriptionResponse updatePrescription(Long id, PrescriptionRequest request) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + id));
        
        prescription.setPrescribedBy(request.getPrescribedBy());
        prescription.setStatus(request.getStatus());

        return mapToResponse(prescriptionRepository.save(prescription));
    }

    @Override
    public void deletePrescription(Long id) {
        if (!prescriptionRepository.existsById(id)) {
            throw new RuntimeException("Prescription not found with id: " + id);
        }
        prescriptionRepository.deleteById(id);
    }

    @Override
    public PrescriptionResponse getPrescriptionById(Long id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + id));
        return mapToResponse(prescription);
    }

    @Override
    public List<PrescriptionResponse> getPrescriptionsByUserName(String userName) {
        return prescriptionRepository.findByUserProfileUserName(userName)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PrescriptionResponse> getAllPrescriptions() {
        return prescriptionRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PrescriptionResponse mapToResponse(Prescription entity) {
        PrescriptionResponse dto = new PrescriptionResponse();
        ArrayList<MedicineResponse>medicineResList = new ArrayList();
        dto.setId(entity.getId());
        for(Medicine med : entity.getMedicines()) {
        	MedicineResponse res = new MedicineResponse(med);
        	medicineResList.add(res);
        }
        dto.setPrescribedBy(entity.getPrescribedBy());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

	@Override
	public void uploadPrescriptions(MultipartFile[] files, String userName) {
		UserProfiles user = userProfileRepository.findByUserName(userName)
	            .orElseThrow(() -> new RuntimeException("User not found: " + userName));
		Prescription prescription = new Prescription();
	    prescription.setUserProfile(user);
	    prescription.setStatus("UPLOADED");
	    for (MultipartFile file : files) {
	        try {
	            PrescriptionFile prescriptionFile = new PrescriptionFile();
	            prescriptionFile.setFileName(file.getOriginalFilename());
	            prescriptionFile.setFileType(file.getContentType());
	            prescriptionFile.setFileContent(file.getBytes());
	            prescriptionFile.setPrescription(prescription);
	            prescription.getFiles().add(prescriptionFile);
	        } catch (IOException e) {
	            throw new RuntimeException("Failed to store file: " + file.getOriginalFilename(), e);
	        }
	    }
	    prescriptionRepository.save(prescription);
	}
	
	@Override
    public ResponseEntity<byte[]> downloadFile(Long fileId) {
        PrescriptionFile file = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(file.getFileType()))
                .body(file.getFileContent());
    }
}
