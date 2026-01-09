package com.financialflow.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financialflow.dto.TransactionDTO;
import com.financialflow.services.TransactionService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RequestMapping("/api/transaction")
@RestController
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody @Valid TransactionDTO transaction,
            @AuthenticationPrincipal Integer userId) {
        TransactionDTO transactionSaved = service.saveTransaction(transaction, userId);
        return ResponseEntity.ok(transactionSaved);
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransaction() {
        return ResponseEntity.ok(service.getAllTransactions());
    }

    @GetMapping("/user")
    public ResponseEntity<List<TransactionDTO>> getTransactionByUserId(@AuthenticationPrincipal Integer userId) {
        return ResponseEntity.ok(service.getTransactionByUserId(userId));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<TransactionDTO>> getTRansactionByCategory(@PathVariable int id,
            @AuthenticationPrincipal Integer userId) {
        return ResponseEntity.ok(service.getTransactionByCategory(id, userId));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByDateRange(
            @PathParam("startDate") String startDate,
            @PathParam("endDate") String endDate,
            @AuthenticationPrincipal Integer userId) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return ResponseEntity.ok(service.getTransactionByDateBetwen(start, end, userId));
    }

    @PutMapping()
    public ResponseEntity<TransactionDTO> updateTransaction(@RequestBody @Valid TransactionDTO transaction,
            @AuthenticationPrincipal Integer userId) {
        TransactionDTO response = service.updateTransaction(transaction, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable int id){
        String response = service.deleteString(id);
        return ResponseEntity.ok(response);
    }

}