package com.financialflow.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financialflow.dto.PeriodPdfDTO;
import com.financialflow.services.GenerateFilesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pdf")
@Tag(name = "PDF", description = "Endpoints for generating PDF reports")
@SecurityRequirement(name = "bearerAuth")
public class PdfController {

    private final GenerateFilesService filesGenearator;

    public PdfController(GenerateFilesService filesGenearator) {
        this.filesGenearator = filesGenearator;
    }

    @Operation(summary = "Generate PDF report", description = "Generates a PDF report of transactions for a given period.")
    @ApiResponse(responseCode = "200", description = "PDF report generated successfully")
    @PostMapping("/generate")
    public ResponseEntity<?> generatePdf(@Valid @RequestBody PeriodPdfDTO period,
            @AuthenticationPrincipal Integer userId) {
        byte[] pdfBytes = filesGenearator.generatePdf(userId, period);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"financial_report.pdf\"")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdfBytes);
    }
}
