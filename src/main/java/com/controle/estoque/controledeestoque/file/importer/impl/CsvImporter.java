package com.controle.estoque.controledeestoque.file.importer.impl;

import com.controle.estoque.controledeestoque.DTO.ProdutoDTo;
import com.controle.estoque.controledeestoque.file.importer.contract.FIleImporter;
import com.controle.estoque.controledeestoque.model.Categoria;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CsvImporter implements FIleImporter {

    private static final Logger log = LoggerFactory.getLogger(CsvImporter.class);

    @Override
    public List<ProdutoDTo> importFile(InputStream stream) throws IOException {
        CSVFormat format = CSVFormat.Builder.create()
                .setDelimiter(',')
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .build();

        try (CSVParser parser = format.parse(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            return parseRecordsToProdutoDTO(parser);
        }
    }

    private List<ProdutoDTo> parseRecordsToProdutoDTO(Iterable<CSVRecord> records) {
        List<ProdutoDTo> produtos = new ArrayList<>();

        for (CSVRecord record : records) {
            try {
                String nome = record.get("nome");

                BigDecimal preco = new BigDecimal(record.get("preco"));

                Integer quantidade = Integer.parseInt(record.get("quantidade"));

                Categoria categoria = Categoria.valueOf(
                        record.get("categoria").toUpperCase()
                );

                String fornecedoresRaw = record.get("fornecedores");

                List<Long> fornecedoresIds = (fornecedoresRaw == null || fornecedoresRaw.isBlank())
                        ? List.of()
                        : Arrays.stream(fornecedoresRaw.split(";"))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(Long::parseLong)
                        .toList();

                ProdutoDTo produto = new ProdutoDTo(
                        null,
                        nome,
                        preco,
                        quantidade,
                        categoria
                );

                produtos.add(produto);

            } catch (Exception e) {
                log.error("Erro ao processar linha {}: {}", record.getRecordNumber(), e.getMessage());
            }
        }

        log.info("Importação finalizada. Total de produtos válidos: {}", produtos.size());

        return produtos;
    }
}