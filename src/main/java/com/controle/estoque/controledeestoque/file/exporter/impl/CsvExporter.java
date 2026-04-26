package com.controle.estoque.controledeestoque.file.exporter.impl;

import com.controle.estoque.controledeestoque.DTO.ProdutoDTo;
import com.controle.estoque.controledeestoque.file.exporter.contract.FileExporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class CsvExporter implements FileExporter {
    @Override
    public Resource exportFile(List<ProdutoDTo> produtos) throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


        outputStream.write(0xEF);
        outputStream.write(0xBB);
        outputStream.write(0xBF);

        OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);

        CSVFormat csvFormat = CSVFormat.Builder.create()
                .setHeader("ID", "nome", "preco", "quantidade", "categoria")
                .setSkipHeaderRecord(false)
                .setDelimiter(';')
                .build();

        try (CSVPrinter printer = new CSVPrinter(writer, csvFormat)) {

            for (ProdutoDTo produto : produtos) {
                printer.printRecord(
                        produto.id(),
                        produto.nome(),
                        formatarPreco(produto.preco()),
                        produto.quantidade(),
                        produto.categoria()
                );
            }

            printer.flush();
        }

        return new ByteArrayResource(outputStream.toByteArray());

    }

    private String formatarPreco(BigDecimal preco) {
        return String.format("R$ %.2f", preco);
    }




}
