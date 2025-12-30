package com.financialflow.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financialflow.dto.loginDTO;
import com.financialflow.security.Auth;
import com.financialflow.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService =  authService;
    }

    @GetMapping("/login")
    public String login(@Valid @RequestBody loginDTO data){
        String token = authService.validateUser(data);
        return token;
    }
}
