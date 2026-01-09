package com.user.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.user.request.AddCategoryRequest;
import com.user.request.AddSubCategoryRequest;
import com.user.service.CategoryService;

@RequestMapping("/master")
@RestController
public class CategoryController {

	    @Autowired
	    private CategoryService categoryService;

	    @PostMapping("/add/category")
	    public ResponseEntity<?> addCategory(@RequestBody AddCategoryRequest req) {
	        return ResponseEntity.ok(categoryService.addCategory(req.getCategoryCode(), req.getCategoryName()));
	    }

	    @PostMapping("/add/subCategory")
	    public ResponseEntity<?> addSubCategory(@RequestBody AddSubCategoryRequest req) {
	        return ResponseEntity.ok(categoryService.addSubCategory(req.getCategoryCode(), req.getSubCategoryName(), req.getCategoryCode()));
	    }

	    @GetMapping("/fetch/allCategories")
	    public ResponseEntity<?> getCategories() {
	        return ResponseEntity.ok(categoryService.getAllCategories());
	    }
	

}
