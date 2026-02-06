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
@RequestMapping("/api/excel")
@Tag(name = "Excel", description = "Endpoints for generating Excel reports")
@SecurityRequirement(name = "bearerAuth")
public class ExcelController {

    private final GenerateFilesService fileGenerator;

    public ExcelController(GenerateFilesService fileGenerator) {
        this.fileGenerator = fileGenerator;
    }
    
    @Operation(summary = "Generate Excel report", description = "Generates an Excel report of transactions for a given period.")
    @ApiResponse(responseCode = "200", description = "Excel report generated successfully")
    @PostMapping("/generate")
    public ResponseEntity<?> generateExcelReport(@Valid @RequestBody PeriodPdfDTO period, 
            @AuthenticationPrincipal Integer userId) {
        byte[] excelBytes = fileGenerator.generateExcel(userId, period);
        return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=report.xlsx")
        .contentType(
            MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            )
        )
        .body(excelBytes);
    }
}
