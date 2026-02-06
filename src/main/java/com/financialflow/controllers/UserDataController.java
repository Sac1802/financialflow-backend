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

import com.financialflow.dto.EmailRequest;
import com.financialflow.dto.PasswordRequest;
import com.financialflow.dto.UserDataDTO;
import com.financialflow.dto.UserResponse;
import com.financialflow.services.UserDataService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@Tag(name = "Users", description = "Endpoints for managing user data")
@SecurityRequirement(name = "bearerAuth")
public class UserDataController {

    private final UserDataService service;

    public UserDataController(UserDataService service){
        this.service = service;
    }
    
    @Operation(summary = "Create a new user", description = "Creates a new user.")
    @ApiResponse(responseCode = "200", description = "User created successfully")
    @PostMapping
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserDataDTO data){
        UserDataDTO response = service.saveUserData(data);
        return ResponseEntity.ok(response);
    }

    @SecurityRequirements()
    @Operation(summary = "Get all users", description = "Retrieves all users from the system.")
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(service.getUsers());
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a user by their ID.")
    @ApiResponse(responseCode = "200", description = "User retrieved successfully")
    @GetMapping("/id")
    public ResponseEntity<?> getUserById(@AuthenticationPrincipal Integer userId){
        UserResponse userFind = service.getUserById(userId);
        return ResponseEntity.ok(userFind);
    }

    @Operation(summary = "Update a user", description = "Updates an existing user.")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDataDTO user,@AuthenticationPrincipal Integer userId){
        UserResponse response = service.updateUser(userId, user);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update user password", description = "Updates the password of the authenticated user.")
    @ApiResponse(responseCode = "200", description = "Password updated successfully")
    @PutMapping("/update/password")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal Integer userId,@RequestBody PasswordRequest newPassword){
        String response = service.updatePasswordUser(userId, newPassword);
        return ResponseEntity.ok(response);
    }

    @SecurityRequirements()
    @Operation(summary = "Validate email", description = "Validates if an email is already in use.")
    @ApiResponse(responseCode = "200", description = "Email validation successful")
    @PostMapping("/validate/email")
    public ResponseEntity<?> validateEmail(@RequestBody EmailRequest email){
        String response = service.verifyEmail(email);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a user", description = "Deletes the authenticated user.")
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    @DeleteMapping
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal Integer userId){
        String response = service.deleteUser(userId);
        return ResponseEntity.status(204).body(response); 
    }
}
