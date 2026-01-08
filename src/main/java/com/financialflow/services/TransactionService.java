package com.financialflow.services;

import org.springframework.stereotype.Service;

import com.financialflow.dto.TransactionDTO;
import com.financialflow.mapper.TransactionMapper;
import com.financialflow.models.Transaction;
import com.financialflow.repository.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository repository;
    private final TransactionMapper mapper;

    public TransactionService(TransactionRepository repository, TransactionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public TransactionDTO saveTransaction(TransactionDTO transactionDTO, int userId){
        Transaction transaction = mapper.convertDtoToTransaction(transactionDTO, userId);
        Transaction saved = repository.save(transaction);
        return mapper.convertTransactionToDto(saved);
    }
}
