package com.financialflow.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.financialflow.dto.loginDTO;
import com.financialflow.models.UserData;
import com.financialflow.repository.UserDataRepository;
import com.financialflow.security.Auth;

@Service
public class AuthService {
    
    private final UserDataRepository repository;
    private final Auth auth;
    private final PasswordEncoder encoder;

    public AuthService(UserDataRepository repository, Auth auth, PasswordEncoder encoder){
        this.repository = repository;
        this.auth = auth;
        this.encoder = encoder;
    }

    public String validateUser(loginDTO data){
        UserData user = repository.findByEmail(data.getEmail()).orElseThrow(() -> 
            new RuntimeException("Invalid email or password")
        );
        if(!encoder.matches(data.getPassword(), user.getPassword()))
            throw new RuntimeException("Invalid email or password");
        return auth.generateToken(user.getId());
    }
}
