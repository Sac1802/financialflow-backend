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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserDataController {

    private final UserDataService service;

    public UserDataController(UserDataService service){
        this.service = service;
    }
    
    @PostMapping
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserDataDTO data){
        UserDataDTO response = service.saveUserData(data);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(service.getUsers());
    }

    @GetMapping("/id")
    public ResponseEntity<?> getUserById(@AuthenticationPrincipal Integer userId){
        UserResponse userFind = service.getUserById(userId);
        return ResponseEntity.ok(userFind);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDataDTO user,@AuthenticationPrincipal Integer userId){
        UserResponse response = service.updateUser(userId, user);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/password")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal Integer userId,@RequestBody PasswordRequest newPassword){
        String response = service.updatePasswordUser(userId, newPassword);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate/email")
    public ResponseEntity<?> validateEmail(@RequestBody EmailRequest email){
        String response = service.verifyEmail(email);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal Integer userId){
        String response = service.deleteUser(userId);
        return ResponseEntity.status(204).body(response); 
    }
}
