package com.controle.estoque.controledeestoque.file.importer.impl;

import com.controle.estoque.controledeestoque.DTO.ProdutoDTo;
import com.controle.estoque.controledeestoque.file.importer.contract.FIleImporter;
import com.controle.estoque.controledeestoque.model.Categoria;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Component
public class XlsxImporter implements FIleImporter {

    private static final Logger log = LoggerFactory.getLogger(XlsxImporter.class);

    @Override
    public List<ProdutoDTo> importFile(InputStream stream) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(stream)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            return parseRowsToProdutoDtoList(rowIterator);
        }
    }

    private List<ProdutoDTo> parseRowsToProdutoDtoList(Iterator<Row> rowIterator) {
        List<ProdutoDTo> produtos = new ArrayList<>();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            if (isRowValid(row)) {
                try {
                    produtos.add(parseRowToProdutoDto(row));
                } catch (Exception e) {
                    log.error("Erro na linha {}: {}", row.getRowNum(), e.getMessage());
                }
            }
        }

        return produtos;
    }

    private ProdutoDTo parseRowToProdutoDto(Row row) {

        String nome = row.getCell(0).getStringCellValue();

        BigDecimal preco = BigDecimal.valueOf(
                row.getCell(1).getNumericCellValue()
        );

        Integer quantidade = (int) row.getCell(2).getNumericCellValue();

        Categoria categoria = Categoria.valueOf(
                row.getCell(3).getStringCellValue().toUpperCase()
        );


        String fornecedoresRaw = row.getCell(4).getStringCellValue();

        List<Long> fornecedoresIds = Arrays.stream(fornecedoresRaw.split(";"))
                .map(Long::parseLong)
                .toList();

        return new ProdutoDTo(
                null,
                nome,
                preco,
                quantidade,
                categoria
        );
    }

    private static boolean isRowValid(Row row) {
        return row.getCell(0) != null &&
                row.getCell(0).getCellType() != CellType.BLANK;
    }
}