package com.financialflow.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financialflow.dto.UserDataDTO;
import com.financialflow.mapper.UserDataMapper;
import com.financialflow.models.UserData;
import com.financialflow.repository.UserDataRepository;

@Service
public class UserDataService {
    private final UserDataRepository repository;
    private final UserDataMapper mapper;

    public UserDataService(UserDataRepository repository, UserDataMapper mapper){
        this.repository = repository;
        this.mapper = mapper;
    }


    @Transactional
    public UserDataDTO saveUserData(UserDataDTO userDTO){
        UserData dataSave = mapper.convertDTOToUser(userDTO);
        UserData response = repository.save(dataSave);
        if(response == null) throw new RuntimeException("The user information could not be saved");
        return mapper.convertUserToDTO(response);
    }
}
