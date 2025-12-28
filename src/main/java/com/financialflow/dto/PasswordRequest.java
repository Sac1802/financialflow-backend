package com.financialflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class PasswordRequest {

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",
            message = "The password must have at least 8 characters, one uppercase, one lowercase, one number" +
                    " and one special character"
    )
    private String newPassword;

    public PasswordRequest(String newPassword){
        this.newPassword = newPassword;
    }

    public PasswordRequest(){

    }

    public String getNewPassword(){
        return newPassword;
    }

    public void setNewPassword(String newPassword){
        this.newPassword = newPassword;
    }
}
