package com.financialflow.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financialflow.dto.EmailRequest;
import com.financialflow.dto.PasswordRequest;
import com.financialflow.dto.UserDataDTO;
import com.financialflow.dto.UserResponse;
import com.financialflow.mapper.UserDataMapper;
import com.financialflow.models.UserData;
import com.financialflow.repository.UserDataRepository;

@Service
public class UserDataService {
    private final UserDataRepository repository;
    private final UserDataMapper mapper;
    private final PasswordEncoder encoder;

    public UserDataService(UserDataRepository repository, UserDataMapper mapper, PasswordEncoder encoder){
        this.repository = repository;
        this.mapper = mapper;
        this.encoder = encoder;
    }


    @Transactional
    public UserDataDTO saveUserData(UserDataDTO userDTO){
        UserData dataSave = mapper.convertDTOToUser(userDTO);
        dataSave.setPassword(encoder.encode(userDTO.getPassword()));
        UserData response = repository.save(dataSave);
        if(response == null) throw new RuntimeException("The user information could not be saved");
        return mapper.convertUserToDTO(response);
    }

    public List<UserResponse> getUsers(){
        return repository.findAll().stream()
            .map(mapper::convertUsertToResponse).toList();
    }

    public UserResponse getUserById(int id){
        UserData userFind = repository.findById(id).orElseThrow(() ->
            new RuntimeException("Not User match"));
        return mapper.convertUsertToResponse(userFind);
    }

    public UserResponse updateUser(int id, UserDataDTO userDto){
        UserData userEntity = repository.findById(id).orElseThrow(() ->
            new RuntimeException("Not User match"));
        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());
        UserData updatedUser = repository.save(userEntity);
        return mapper.convertUsertToResponse(updatedUser);
    }

    public String updatePasswordUser(int id, PasswordRequest newPassword){
        UserData userEntity = repository.findById(id).orElseThrow(() ->
            new RuntimeException("Not User match"));
        userEntity.setPassword(encoder.encode(newPassword.getNewPassword()));
        repository.save(userEntity);
        return "Password update Successfully";
    }

    public String verifyEmail(EmailRequest email){
        Optional<UserData> emailVerify = repository.findByEmail(email.getEmail());
        if(emailVerify.isEmpty()){
            return "Email not found";
        }else{
            return "Email verify Successfully";
        }
    }

    public String deleteUser(int id){
        repository.deleteById(id);
        return "User delette successfully";
    }
}
