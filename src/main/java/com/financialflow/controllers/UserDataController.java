package com.financialflow.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.financialflow.dto.UserDataDTO;
import com.financialflow.services.UserDataService;

import jakarta.validation.Valid;

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
}
