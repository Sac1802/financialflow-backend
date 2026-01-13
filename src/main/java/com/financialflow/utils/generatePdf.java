package com.financialflow.utils;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import java.awt.Color;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import com.financialflow.dto.TransactionDTO;
import com.financialflow.models.TransactionType;

@Service
public class generatePdf {

    private void addHeaderCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(new Color(230, 230, 230));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(6);
        table.addCell(cell);
    }

    public byte[] createPdf(List<TransactionDTO> transactions,
            String userName,
            String period) {

        try {
            ByteArrayOutputStream pdfOut = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4, 36, 36, 50, 36);
            PdfWriter.getInstance(document, pdfOut);
            document.open();

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font headerFont = new Font(Font.HELVETICA, 11, Font.BOLD);
            Font normalFont = new Font(Font.HELVETICA, 10);

            Paragraph title = new Paragraph("Financial Transactions Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            Paragraph info = new Paragraph(
                    "User: " + userName + "\n" +
                            "Period: " + period + "\n" +
                            "Generated on: " + LocalDate.now(),
                    normalFont);
            info.setSpacingAfter(15);
            document.add(info);

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.setWidths(new float[] { 2f, 4f, 2f, 2f, 2f });

            addHeaderCell(table, "Date", headerFont);
            addHeaderCell(table, "Description", headerFont);
            addHeaderCell(table, "Category", headerFont);
            addHeaderCell(table, "Type", headerFont);
            addHeaderCell(table, "Amount", headerFont);

            BigDecimal totalIncome = BigDecimal.ZERO;
            BigDecimal totalExpense = BigDecimal.ZERO;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (TransactionDTO tx : transactions) {

                table.addCell(new Phrase(tx.getDate().format(formatter), normalFont));
                table.addCell(new Phrase(
                        tx.getDescription() != null ? tx.getDescription() : "-", normalFont));
                table.addCell(new Phrase(String.valueOf(tx.getCategory()), normalFont));
                table.addCell(new Phrase(tx.getTransactionType().name(), normalFont));

                PdfPCell amountCell = new PdfPCell(
                        new Phrase("$ " + tx.getAmount().setScale(2), normalFont));
                amountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

                if (tx.getTransactionType() == TransactionType.INCOME) {
                    amountCell.setPhrase(new Phrase("+ $ " + tx.getAmount().setScale(2), normalFont));
                    totalIncome = totalIncome.add(tx.getAmount());
                } else {
                    amountCell.setPhrase(new Phrase("- $ " + tx.getAmount().setScale(2), normalFont));
                    totalExpense = totalExpense.add(tx.getAmount());
                }

                table.addCell(amountCell);
            }

            document.add(table);

            document.add(new Paragraph(""));

            Paragraph totals = new Paragraph(
                    "Total Income: $ " + totalIncome.setScale(2) + "\n" +
                            "Total Expense: $ " + totalExpense.setScale(2) + "\n" +
                            "Balance: $ " + totalIncome.subtract(totalExpense).setScale(2),
                    headerFont);
            totals.setAlignment(Element.ALIGN_RIGHT);
            document.add(totals);

            document.close();
            return pdfOut.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }

}
