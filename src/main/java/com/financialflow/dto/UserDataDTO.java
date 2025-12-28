package com.financialflow.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDataDTO {
    
    @Size(min = 2, message = "The username must contain at least 2 characters.")
    private String name;

    @Email(message = "Email format invalid")
    @NotBlank(message = "THe email can is not blank")
    private String email;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",
            message = "The password must have at least 8 characters, one uppercase, one lowercase, one number" +
                    " and one special character"
    )
    private String password;


    public UserDataDTO(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserDataDTO(){

    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
