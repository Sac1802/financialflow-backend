package com.financialflow.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.financialflow.dto.TransactionDTO;
import com.financialflow.mapper.TransactionMapper;
import com.financialflow.models.Category;
import com.financialflow.models.Transaction;
import com.financialflow.repository.CategoryRepository;
import com.financialflow.repository.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository repository;
    private final CategoryRepository categoryRepository;
    private final TransactionMapper mapper;

    public TransactionService(TransactionRepository repository, TransactionMapper mapper,
            CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    public TransactionDTO saveTransaction(TransactionDTO transactionDTO, int userId) {
        Transaction transaction = mapper.convertDtoToTransaction(transactionDTO, userId);
        Transaction saved = repository.save(transaction);
        return mapper.convertTransactionToDto(saved);
    }

    public List<TransactionDTO> getAllTransactions() {
        List<Transaction> transactionList = repository.findAll();
        return transactionList.stream()
                .map(mapper::convertTransactionToDto)
                .toList();
    }

    public List<TransactionDTO> getTransactionByUserId(int userId) {
        List<Transaction> transactionsList = repository.findByUser_Id(userId);
        return transactionsList.stream()
                .map(mapper::convertTransactionToDto)
                .toList();
    }

    public TransactionDTO getById(int id) {
        Transaction transaction = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        return mapper.convertTransactionToDto(transaction);
    }

    public Transaction getTransactionById(int id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public TransactionDTO updateTransaction(TransactionDTO transaction, int id) {
        Transaction findTransaction = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        findTransaction.setAmount(transaction.getAmount());
        findTransaction.setDescription(transaction.getDescription());
        Category category = categoryRepository.findById(transaction.getCategory())
                .orElseThrow(() -> new RuntimeException("Not match category found"));
        findTransaction.setCategory(category);
        findTransaction.setDate(transaction.getDate());
        findTransaction.setTransactionType(transaction.getTransactionType());
        Transaction updatedTransaction = repository.save(findTransaction);
        return mapper.convertTransactionToDto(updatedTransaction);
    }

    public String deleteString(int id) {
        repository.deleteById(id);
        return "Transaction with id " + id + " has been deleted.";
    }

    public List<TransactionDTO> getTransactionByDateBetwen(LocalDate starDate,
            LocalDate endDate, int userId) {
        List<Transaction> transactionList = repository.findByUser_IdAndDateBetween(userId, starDate, endDate);
        return transactionList.stream()
                .map(mapper::convertTransactionToDto)
                .toList();
    }

    public List<TransactionDTO> getTransactionByCategory(int idCAtegory, int userId) {
        List<Transaction> transactions = repository.findByCategory_IdAndUser_Id(idCAtegory, userId);
        return transactions.stream()
                .map(mapper::convertTransactionToDto)
                .toList();
    }
}
