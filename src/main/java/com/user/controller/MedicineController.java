package com.user.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user.entity.Medicine;
import com.user.master.entity.MedicineCategory;
import com.user.master.entity.MedicineSubCategory;
import com.user.request.MedicineRequest;
import com.user.response.CategoryResponse;
import com.user.response.MedicineResponse;
import com.user.service.MedicineService;

@RestController
@RequestMapping("/medicine")
public class MedicineController {
	
	@Autowired
	private MedicineService medicineService;
	
	@GetMapping("fetchMedicine/{medicineCode}")
	public MedicineResponse fetchMedicineByMedicineCode(@PathVariable String medicineCode){
		MedicineResponse res =  medicineService.fetchMedicineByMedicineCode(medicineCode);
		return res;
	}
	
	 // ✅ CREATE
    @PostMapping("/addMedicine")
    public ResponseEntity<String> create(@RequestBody MedicineRequest req) {
    	medicineService.addMedicine(req);
        return ResponseEntity.ok("Medicine created successfully!!");
    }
    
    @GetMapping
    public ResponseEntity<List<MedicineResponse>> getAll() {
        return ResponseEntity.ok(medicineService.getAll());
    }
    
    @GetMapping("/{medicineCode}/{batchNumber}")
    public ResponseEntity<Medicine> getByCodeAndBatch(@PathVariable String medicineCode, @PathVariable String batchNumber) {
        return ResponseEntity.ok(medicineService.getByCodeAndBatch(medicineCode, batchNumber));
    }
    
    @PutMapping("/{medicineCode}/{batchNumber}")
    public ResponseEntity<Medicine> update(@PathVariable String medicineCode, @PathVariable String batchNumber, @RequestBody MedicineRequest req) {
        return ResponseEntity.ok(medicineService.updateByCodeAndBatch(medicineCode, batchNumber, req));
    }

    @DeleteMapping("/{medicineCode}/{batchNumber}")
    public ResponseEntity<String> delete(@PathVariable String medicineCode, @PathVariable String batchNumber) {
        medicineService.deleteByCodeAndBatch(medicineCode, batchNumber);
        return ResponseEntity.ok("Medicine deleted successfully for code: " + medicineCode + " and batch: " + batchNumber);
    }
    
    @GetMapping("/by-name")
    public List<Medicine> getByName(@RequestParam String name) {
        return medicineService.getByName(name);
    }

    @GetMapping("/by-code/{code}")
    public Medicine getByCode(@PathVariable String code) {
        return medicineService.getByCode(code);
    }

    @GetMapping("/by-brand")
    public List<Medicine> getByBrand(@RequestParam String brandName) {
        return medicineService.getByBrandName(brandName);
    }

    @GetMapping("/by-manufacturer")
    public List<Medicine> getByManufacturer(@RequestParam String manufacturer) {
        return medicineService.getByManufacturer(manufacturer);
    }
    @GetMapping("/search/fullText")
    public List<MedicineResponse> searchMedicines(@RequestParam String query) {
        return medicineService.searchMedicinesFullText(query);
    }
    
    @GetMapping("/search/keyword") //This is very important for searching.
    public List<MedicineResponse> searchMedicinesByKeyword(@RequestParam String query) {
        return medicineService.searchMedicines(query);
    }
    

    @GetMapping("/active")
    public ResponseEntity<?> getActiveMedicines() {
        return ResponseEntity.ok(medicineService.findAllActiveMedicines());
    }

    @GetMapping("/expired")
    public ResponseEntity<?> getExpiredMedicines() {
        return ResponseEntity.ok(medicineService.findExpiredMedicines());
    }
    
    @GetMapping("/user/categories")
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        return ResponseEntity.ok(medicineService.getAllCategories());
    }
    
    @GetMapping("/user/subCategories")
    public ResponseEntity<List<MedicineSubCategory>> getSubCategories() {
        return ResponseEntity.ok(medicineService.getAllSubCategories());
    }
    
    @GetMapping("/user/fetchMedicine/{subCategoryCode}")
	public List<MedicineResponse> fetchMedicineBySuCatCodeForUser(@PathVariable String subCategoryCode){
		return medicineService.fetchMedicineBySubCatCode(subCategoryCode);
		
	}
    
    
    


}
