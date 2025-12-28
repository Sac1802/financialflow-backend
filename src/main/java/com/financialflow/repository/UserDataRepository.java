package com.financialflow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financialflow.models.UserData;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Integer>{
    
    Optional<UserData> findByEmail(String email);
}
