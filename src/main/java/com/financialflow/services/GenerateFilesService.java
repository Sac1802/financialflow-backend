package com.financialflow.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.financialflow.dto.PeriodPdfDTO;
import com.financialflow.dto.TransactionDTO;
import com.financialflow.models.UserData;
import com.financialflow.utils.GenerateExcel;
import com.financialflow.utils.generatePdf;

@Service
public class GenerateFilesService {

    private final generatePdf generatePdf;
    private final GenerateExcel generateExcel;
    private final UserDataService userService;
    private final TransactionService transactionService;

    public GenerateFilesService(generatePdf generatePdf, GenerateExcel generateExcel, UserDataService userService,
            TransactionService transactionService) {
        this.generatePdf = generatePdf;
        this.generateExcel = generateExcel;
        this.userService = userService;
        this.transactionService = transactionService;
    }
    

    public byte[] generatePdf(int userId, PeriodPdfDTO period){
        UserData user = userService.findUserById(userId);
        List<TransactionDTO> listTrsansactions = transactionService.getTransactionByDateBetwen(
                period.getStarDate(), period.getEndDate(), userId);
        String periodString = "From " + period.getStarDate().toString() + " to " + period.getEndDate().toString();
        byte[] pdfBytes = generatePdf.createPdf(listTrsansactions, user.getName(), periodString);
        return pdfBytes;
    }

    public byte[] generateExcel(int userId, PeriodPdfDTO period){
        List<TransactionDTO> listTrsansactions = transactionService.getTransactionByDateBetwen(
                period.getStarDate(), period.getEndDate(), userId);
        byte[] excelBytes = generateExcel.createExcel(listTrsansactions);
        return excelBytes;
    }
}
