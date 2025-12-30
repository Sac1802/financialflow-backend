package com.financialflow.dto;

import com.financialflow.models.TransactionType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CategoryDTO {
    
    @Size(min = 2, message = "The username must contain at least 2 characters.")
    private String name;


    @NotNull(message = "Transaction type is required")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;


    public CategoryDTO(String name, TransactionType transactionType){
        this.name = name;
        this.transactionType = transactionType;
    }

    public CategoryDTO(){

    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public TransactionType getTransactionType(){
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType){
        this.transactionType = transactionType;
    }
}
