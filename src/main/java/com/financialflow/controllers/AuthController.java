package com.financialflow.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financialflow.dto.loginDTO;
import com.financialflow.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoint for logging in and generating a token")
public class AuthController {
    
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService =  authService;
    }

    @Operation(summary = "Authenticate user", description = "Authenticate a user with username and password, returns a JWT token if successful.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Authentication successful, returns JWT token"),
        @ApiResponse(responseCode = "400", description = "Invalid user credentials")
    })
    @PostMapping("/login")
    public String login(@Valid @RequestBody loginDTO data){
        String token = authService.validateUser(data);
        return token;
    }
}
