package com.financialflow.mapper;


import org.springframework.stereotype.Component;

import com.financialflow.dto.UserDataDTO;
import com.financialflow.dto.UserResponse;
import com.financialflow.models.UserData;

@Component
public class UserDataMapper {
    
    public UserData convertDTOToUser(UserDataDTO data){
        UserData user = new UserData();
        user.setName(data.getName());
        user.setEmail(data.getEmail());
        user.setPassword(data.getPassword());
        return user;
    }

    public UserDataDTO convertUserToDTO(UserData data){
        UserDataDTO user = new UserDataDTO();
        user.setName(data.getName());
        user.setEmail(data.getEmail());
        user.setPassword(data.getPassword());
        return user;
    }

    public UserResponse convertUsertToResponse(UserData data){
        UserResponse user = new UserResponse();
        user.setId(data.getId());
        user.setName(data.getName());
        user.setEmail(data.getEmail());
        return user;
    }
}
