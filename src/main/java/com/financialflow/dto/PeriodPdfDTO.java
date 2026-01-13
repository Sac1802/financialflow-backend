package com.financialflow.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.PastOrPresent;

public class PeriodPdfDTO {
    
    @PastOrPresent(message = "The start date must be in the past or present")
    private LocalDate starDate;

    @PastOrPresent(message = "The end date must be in the past or present")
    private LocalDate endDate;

    public PeriodPdfDTO(LocalDate startDate, LocalDate endDate){
        this.starDate = startDate;
        this.endDate = endDate;
    }

    public PeriodPdfDTO(){
    }

    public LocalDate getStarDate() {
        return starDate;
    }

    public void setStarDate(LocalDate starDate) {
        this.starDate = starDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
