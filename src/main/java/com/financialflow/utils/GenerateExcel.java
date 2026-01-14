package com.financialflow.utils;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.financialflow.dto.TransactionDTO;
import com.financialflow.models.TransactionType;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

@Service
public class GenerateExcel {

    public byte[] createExcel(List<TransactionDTO> transactions) {
        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Financial Report");

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);

            CellStyle dateStyle = workbook.createCellStyle();
            dateStyle.setDataFormat(
                    workbook.getCreationHelper()
                            .createDataFormat()
                            .getFormat("dd/MM/yyyy"));

            CellStyle moneyStyle = workbook.createCellStyle();
            moneyStyle.setDataFormat(
                    workbook.getCreationHelper()
                            .createDataFormat()
                            .getFormat("$#,##0.00"));

            CellStyle textStyle = workbook.createCellStyle();
            textStyle.setBorderBottom(BorderStyle.THIN);

            Row header = sheet.createRow(0);
            String[] columns = { "Date", "Type", "Amount", "Description" };

            for (int i = 0; i < columns.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            BigDecimal totalIncome = BigDecimal.ZERO;
            BigDecimal totalExpense = BigDecimal.ZERO;

            for (TransactionDTO tx : transactions) {
                Row row = sheet.createRow(rowNum++);

                Cell dateCell = row.createCell(0);
                dateCell.setCellValue(tx.getDate());
                dateCell.setCellStyle(dateStyle);

                row.createCell(1).setCellValue(tx.getTransactionType().name());

                Cell amountCell = row.createCell(2);
                amountCell.setCellValue(tx.getAmount().doubleValue());
                amountCell.setCellStyle(moneyStyle);

                row.createCell(3).setCellValue(
                        tx.getDescription() != null ? tx.getDescription() : "-");

                if (tx.getTransactionType() == TransactionType.INCOME) {
                    totalIncome = totalIncome.add(tx.getAmount());
                } else {
                    totalExpense = totalExpense.add(tx.getAmount());
                }
            }

            Row totalRow = sheet.createRow(rowNum + 1);

            totalRow.createCell(1).setCellValue("Total Income");
            Cell incomeCell = totalRow.createCell(2);
            incomeCell.setCellValue(totalIncome.doubleValue());
            incomeCell.setCellStyle(moneyStyle);

            Row expenseRow = sheet.createRow(rowNum + 2);
            expenseRow.createCell(1).setCellValue("Total Expense");
            Cell expenseCell = expenseRow.createCell(2);
            expenseCell.setCellValue(totalExpense.doubleValue());
            expenseCell.setCellStyle(moneyStyle);

            Row balanceRow = sheet.createRow(rowNum + 3);
            balanceRow.createCell(1).setCellValue("Balance");
            Cell balanceCell = balanceRow.createCell(2);
            balanceCell.setCellValue(
                    totalIncome.subtract(totalExpense).doubleValue());
            balanceCell.setCellStyle(moneyStyle);

            sheet.createFreezePane(0, 1);

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generating Excel", e);
        }
    }

}
