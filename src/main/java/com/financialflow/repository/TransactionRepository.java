package com.financialflow.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financialflow.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByUser_Id(int userId);

    List<Transaction> findByUser_IdAndDateBetween(
        int userId,
        LocalDate startDate,
        LocalDate endDate
    );

    List<Transaction> findByCategory_IdAndUser_Id(
        int categoryId,
        int userId
    );
}

