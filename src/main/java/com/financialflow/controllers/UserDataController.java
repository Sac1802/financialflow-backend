package com.financialflow.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id){
        UserResponse userFind = service.getUserById(id);
        return ResponseEntity.ok(userFind);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDataDTO user,@PathVariable int id){
        UserResponse response = service.updateUser(id, user);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/password/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable int id,@RequestBody PasswordRequest newPassword){
        String response = service.updatePasswordUser(id, newPassword);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate/email")
    public ResponseEntity<?> validateEmail(@RequestBody EmailRequest email){
        String response = service.verifyEmail(email);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id){
        String response = service.deleteUser(id);
        return ResponseEntity.status(204).body(response); 
    }
}
