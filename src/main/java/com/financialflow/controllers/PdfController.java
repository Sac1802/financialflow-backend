package com.financialflow.controllers;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financialflow.dto.PeriodPdfDTO;
import com.financialflow.dto.TransactionDTO;
import com.financialflow.models.UserData;
import com.financialflow.services.TransactionService;
import com.financialflow.services.UserDataService;
import com.financialflow.utils.generatePdf;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {
    private final generatePdf pdfGenerator;
    private final UserDataService userService;
    private final TransactionService transactionService;

    public PdfController(generatePdf pdfGenerator, UserDataService userService,
            TransactionService transactionService) {
        this.pdfGenerator = pdfGenerator;
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping("/generate")
    public ResponseEntity<?> generatePdf(@Valid @RequestBody PeriodPdfDTO period,
            @AuthenticationPrincipal Integer userId) {
        UserData user = userService.findUserById(userId);
        List<TransactionDTO> listTrsansactions = transactionService.getTransactionByDateBetwen(
                period.getStarDate(), period.getEndDate(), userId);
        String periodString = "From " + period.getStarDate().toString() + " to " + period.getEndDate().toString();
        byte[] pdfBytes = pdfGenerator.createPdf(listTrsansactions, user.getName(), periodString);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"financial_report.pdf\"")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdfBytes);
    }
}
