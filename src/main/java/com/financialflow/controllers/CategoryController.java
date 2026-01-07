package com.financialflow.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financialflow.dto.CategoryDTO;
import com.financialflow.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createCategory(
            @RequestBody @Valid CategoryDTO data,
            @AuthenticationPrincipal Integer userId) {
        CategoryDTO categorySaved = service.saveCategory(data, userId);
        return ResponseEntity.ok(categorySaved);
    }

    @GetMapping
    public ResponseEntity<?> getCategoryByUser(@AuthenticationPrincipal Integer userId) {
        return ResponseEntity.ok(service.getCategoriesByUser(userId));
    }

    @GetMapping()
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(service.getAllCategories());
    }

    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestBody @Valid CategoryDTO category,
            @AuthenticationPrincipal Integer userId) {
        CategoryDTO categoryUpdated = service.updateCategory(category, userId);
        return ResponseEntity.ok(categoryUpdated);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCategory(@AuthenticationPrincipal Integer userId){
        String response = service.deleteCategory(userId);
        return ResponseEntity.status(204).body(response);
    }
}
