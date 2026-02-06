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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "Endpoints for managing categories")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @Operation(summary = "Create a new category", description = "Creates a new category for the authenticated user.")
    @ApiResponse(responseCode = "200", description = "Category created successfully")
    @PostMapping
    public ResponseEntity<?> createCategory(
            @RequestBody @Valid CategoryDTO data,
            @AuthenticationPrincipal Integer userId) {
        CategoryDTO categorySaved = service.saveCategory(data, userId);
        return ResponseEntity.ok(categorySaved);
    }


    @Operation(summary = "Get categories by user", description = "Retrieves all categories for the authenticated user.")
    @ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    @GetMapping("/id")
    public ResponseEntity<?> getCategoryByUser(@AuthenticationPrincipal Integer userId) {
        return ResponseEntity.ok(service.getCategoriesByUser(userId));
    }

    @Operation(summary = "Get all categories", description = "Retrieves all categories from the system.")
    @ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    @GetMapping()
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(service.getAllCategories());
    }

    @Operation(summary = "Update a category", description = "Updates an existing category for the authenticated user.")
    @ApiResponse(responseCode = "200", description = "Category updated successfully")
    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestBody @Valid CategoryDTO category,
            @AuthenticationPrincipal Integer userId) {
        CategoryDTO categoryUpdated = service.updateCategory(category, userId);
        return ResponseEntity.ok(categoryUpdated);
    }

    @Operation(summary = "Delete a category", description = "Deletes a category for the authenticated user.")
    @ApiResponse(responseCode = "204", description = "Category deleted successfully")
    @DeleteMapping
    public ResponseEntity<?> deleteCategory(@AuthenticationPrincipal Integer userId){
        String response = service.deleteCategory(userId);
        return ResponseEntity.status(204).body(response);
    }
}
