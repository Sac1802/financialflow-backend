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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RequestMapping("/api/transaction")
@RestController
@Tag(name = "Transactions", description = "Endpoints for managing transactions")
@SecurityRequirement(name = "bearerAuth")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @Operation(summary = "Create a new transaction", description = "Creates a new transaction for the authenticated user.")
    @ApiResponse(responseCode = "200", description = "Transaction created successfully")
    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody @Valid TransactionDTO transaction,
            @AuthenticationPrincipal Integer userId) {
        TransactionDTO transactionSaved = service.saveTransaction(transaction, userId);
        return ResponseEntity.ok(transactionSaved);
    }

    @Operation(summary = "Get all transactions", description = "Retrieves all transactions from the system.")
    @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully")
    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransaction() {
        return ResponseEntity.ok(service.getAllTransactions());
    }

    @Operation(summary = "Get transactions by user", description = "Retrieves all transactions for the authenticated user.")
    @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully")
    @GetMapping("/user")
    public ResponseEntity<List<TransactionDTO>> getTransactionByUserId(@AuthenticationPrincipal Integer userId) {
        return ResponseEntity.ok(service.getTransactionByUserId(userId));
    }

    @Operation(summary = "Get transactions by category", description = "Retrieves all transactions for a given category and the authenticated user.")
    @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully")
    @GetMapping("/category/{id}")
    public ResponseEntity<List<TransactionDTO>> getTRansactionByCategory(@PathVariable int id,
            @AuthenticationPrincipal Integer userId) {
        return ResponseEntity.ok(service.getTransactionByCategory(id, userId));
    }

    @Operation(summary = "Get transactions by date range", description = "Retrieves all transactions within a given date range for the authenticated user.")
    @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully")
    @GetMapping("/date-range")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByDateRange(
            @PathParam("startDate") String startDate,
            @PathParam("endDate") String endDate,
            @AuthenticationPrincipal Integer userId) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return ResponseEntity.ok(service.getTransactionByDateBetwen(start, end, userId));
    }

    @Operation(summary = "Update a transaction", description = "Updates an existing transaction for the authenticated user.")
    @ApiResponse(responseCode = "200", description = "Transaction updated successfully")
    @PutMapping()
    public ResponseEntity<TransactionDTO> updateTransaction(@RequestBody @Valid TransactionDTO transaction,
            @AuthenticationPrincipal Integer userId) {
        TransactionDTO response = service.updateTransaction(transaction, userId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a transaction", description = "Deletes a transaction by its ID.")
    @ApiResponse(responseCode = "200", description = "Transaction deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable int id){
        String response = service.deleteString(id);
        return ResponseEntity.ok(response);
    }

}