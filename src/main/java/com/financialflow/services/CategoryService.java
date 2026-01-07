package com.financialflow.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.financialflow.dto.CategoryDTO;
import com.financialflow.mapper.CategoryMapper;
import com.financialflow.models.Category;
import com.financialflow.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public CategoryService(CategoryRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public CategoryDTO saveCategory(CategoryDTO categoryDTO, int id) {
        Category categoryConvert = mapper.convertDTOToCategory(categoryDTO, id);
        CategoryDTO categorySaved = mapper.convertCategoryToDTO(repository.save(categoryConvert));
        return categorySaved;
    }

    public List<CategoryDTO> getAllCategories() {
        List<Category> categoryFind = repository.findAll();
        return categoryFind.stream()
                .map(mapper::convertCategoryToDTO)
                .toList();
    }

    public List<CategoryDTO> getCategoriesByUser(int id) {
        List<Category> categories = repository.findByUser_Id(id);
        return categories.stream()
                .map(mapper::convertCategoryToDTO)
                .toList();
    }

    public CategoryDTO getCategoryById(int id) {
        Category categoryFind = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return mapper.convertCategoryToDTO(categoryFind);
    }

    public Category getCategoryByIdCategory(int id) {
        Category categoryFind = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return categoryFind;
    }

    public CategoryDTO updateCategory(CategoryDTO categoryDTO, int id) {
        Category categoryFind = repository.findById(id).orElseThrow();
        categoryFind.setName(categoryDTO.getName());
        categoryFind.setTransactionType(categoryDTO.getTransactionType());
        Category categoryUpdated = repository.save(categoryFind);
        return mapper.convertCategoryToDTO(categoryUpdated);
    }

    public String deleteCategory(int id) {
        repository.deleteById(id);
        return "Category deleted successfully";
    }
}
