package com.financialflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financialflow.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
    
    List<Category> findByUser_Id(int id);
}
