package com.financialflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.financialflow.dto.NumberCategories;
import com.financialflow.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findByUser_Id(int id);

    @Query(value = "SELECT c.id AS idCategory, " +
            "c.name AS nameCategory, " +
            "COUNT(t.id) AS totalAmount " +
            "FROM categories c " +
            "LEFT JOIN transactions t ON t.category_id = c.id " +
            "WHERE c.user_id = :userId " +
            "GROUP BY c.id, c.name", nativeQuery = true)
    List<NumberCategories> findNumberCategories(@Param("userId") int userId);
}
