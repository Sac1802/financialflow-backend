package com.financialflow.mapper;

import org.springframework.stereotype.Component;

import com.financialflow.dto.TransactionDTO;
import com.financialflow.models.Category;
import com.financialflow.models.Transaction;
import com.financialflow.models.UserData;
import com.financialflow.services.CategoryService;
import com.financialflow.services.UserDataService;

@Component
public class TransactionMapper {

    private final CategoryService service;
    private final UserDataService userService;

    public TransactionMapper(CategoryService service, UserDataService userService) {
        this.service = service;
        this.userService = userService;
    }

    public TransactionDTO convertTransactionToDto(Transaction transaction){
        TransactionDTO dto = new TransactionDTO();
        dto.setAmount(transaction.getAmount());
        dto.setTransactionType(transaction.getTransactionType());
        dto.setDescription(transaction.getDescription());
        dto.setDate(transaction.getDate());
        dto.setCategory(transaction.getCategory().getId());
        return dto;
    }

    public Transaction convertDtoToTransaction(TransactionDTO dto, int userId){
        Transaction transaction = new Transaction();
        transaction.setAmount(dto.getAmount());
        transaction.setTransactionType(dto.getTransactionType());
        transaction.setDescription(dto.getDescription());
        transaction.setDate(dto.getDate());
        Category category = service.getCategoryByIdCategory(dto.getCategory());
        transaction.setCategory(category);
        UserData user = userService.findUserById(userId);
        transaction.setUser(user);
        return transaction;
    }
}
