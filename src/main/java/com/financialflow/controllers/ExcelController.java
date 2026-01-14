package com.financialflow.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financialflow.dto.PeriodPdfDTO;
import com.financialflow.services.GenerateFilesService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    private final GenerateFilesService fileGenerator;

    public ExcelController(GenerateFilesService fileGenerator) {
        this.fileGenerator = fileGenerator;
    }
    
    @GetMapping("/generate")
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
