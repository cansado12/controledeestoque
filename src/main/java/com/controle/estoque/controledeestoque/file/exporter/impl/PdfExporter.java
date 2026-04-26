package com.controle.estoque.controledeestoque.file.exporter.impl;

import com.controle.estoque.controledeestoque.DTO.ProdutoDTo;
import com.controle.estoque.controledeestoque.file.exporter.contract.FileExporter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class PdfExporter implements FileExporter {

    @Override
    public Resource exportFile(List<ProdutoDTo> produtos) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);

            document.open();
            document.add(new Paragraph("Relatorio de Produtos", new Font(Font.HELVETICA, 16, Font.BOLD)));
            document.add(new Paragraph(" "));
            document.add(createTable(produtos));
            document.close();

            return new ByteArrayResource(outputStream.toByteArray());
        } catch (DocumentException ex) {
            throw new IOException("Erro ao gerar PDF", ex);
        }
    }

    private PdfPTable createTable(List<ProdutoDTo> produtos) {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        try {
            table.setWidths(new float[]{1.2f, 3.4f, 2.0f, 1.8f, 2.2f});
        } catch (DocumentException ex) {
            throw new IllegalStateException("Nao foi possivel configurar a tabela do PDF", ex);
        }

        addHeader(table, "ID");
        addHeader(table, "Nome");
        addHeader(table, "Preco");
        addHeader(table, "Quantidade");
        addHeader(table, "Categoria");

        for (ProdutoDTo produto : produtos) {
            table.addCell(String.valueOf(produto.id()));
            table.addCell(produto.nome());
            table.addCell(produto.preco().toString());
            table.addCell(String.valueOf(produto.quantidade()));
            table.addCell(produto.categoria().name());
        }

        return table;
    }

    private void addHeader(PdfPTable table, String value) {
        PdfPCell cell = new PdfPCell(new Phrase(value, new Font(Font.HELVETICA, 11, Font.BOLD)));
        table.addCell(cell);
    }
}
