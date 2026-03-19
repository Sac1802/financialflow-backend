package com.financialflow.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.financialflow.models.TransactionType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public class TransactionDTO {

    @Min(value = 0, message = "Amount must be non-negative")
    private BigDecimal amount;

    @NotNull(message = "Transaction type is required")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Size(max = 255, message = "Description can have a maximum of 255 characters")
    private String description;

    @PastOrPresent(message = "Date must be in the past or present")
    @NotNull(message = "Date is required")
    private LocalDate date;

    @Min(1)
    private int category;

    public TransactionDTO(BigDecimal amount, TransactionType transactionType,
            String description, LocalDate date, int category) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.description = description;
        this.date = date;
        this.category = category;
    }

    public TransactionDTO() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}