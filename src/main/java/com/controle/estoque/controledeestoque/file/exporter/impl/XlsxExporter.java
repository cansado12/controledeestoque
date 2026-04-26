package com.controle.estoque.controledeestoque.file.exporter.impl;

import com.controle.estoque.controledeestoque.DTO.ProdutoDTo;

import com.controle.estoque.controledeestoque.file.exporter.contract.FileExporter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class XlsxExporter implements FileExporter {
    @Override
    public Resource exportFile(List<ProdutoDTo> produtos) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("produtos");


            CellStyle headerStyle = createHeaderCellStyle(workbook);
            CellStyle currencyStyle = createCurrencyStyle(workbook);

            // 🔹 Header
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Nome", "Preço", "Quantidade", "Categoria"};

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // 🔹 Dados
            int rowIndex = 1;
            for (ProdutoDTo produto : produtos) {
                Row row = sheet.createRow(rowIndex++);

                row.createCell(0).setCellValue(produto.id());
                row.createCell(1).setCellValue(produto.nome());

                Cell precoCell = row.createCell(2);
                precoCell.setCellValue(produto.preco().doubleValue());
                precoCell.setCellStyle(currencyStyle);
                row.createCell(3).setCellValue(produto.quantidade());
                row.createCell(4).setCellValue(produto.categoria().toString());
            }

            // 🔹 Ajustar TODAS as colunas
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayResource(outputStream.toByteArray());
        }
    }

    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);

        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private CellStyle createCurrencyStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();

        style.setDataFormat(format.getFormat("R$ #,##0.00"));
        return style;
    }
}
