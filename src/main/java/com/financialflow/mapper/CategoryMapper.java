package com.financialflow.mapper;

import org.springframework.stereotype.Component;

import com.financialflow.dto.CategoryDTO;
import com.financialflow.models.Category;
import com.financialflow.models.UserData;
import com.financialflow.services.UserDataService;

@Component
public class CategoryMapper {

    private final UserDataService service;
    
    public CategoryMapper(UserDataService service){
        this.service = service;
    }
    
    public Category convertDTOToCategory(CategoryDTO data, int id){
        Category category = new Category();
        category.setName(data.getName());
        category.setTransactionType(data.getTransactionType());
        UserData user = service.findUserById(id);
        if(user == null){
            throw new RuntimeException("Id not found");
        }
        category.setUser(user);
        return category;
    }

    public CategoryDTO convertCategoryToDTO(Category data){
        CategoryDTO category = new CategoryDTO();
        category.setName(data.getName());
        category.setTransactionType(data.getTransactionType());
        return category;
    }

    private UserData findUserById(int id){
        UserData userFind = service.findUserById(id);
        return userFind;
    }
}
