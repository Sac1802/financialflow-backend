package com.financialflow.services;

import org.springframework.stereotype.Service;

import com.financialflow.repository.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository){
        this.repository = repository;
    }    
}
