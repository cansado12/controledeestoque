package com.controle.estoque.controledeestoque.file.exporter.factory;


import com.controle.estoque.controledeestoque.exception.BadRequestException;
import com.controle.estoque.controledeestoque.file.exporter.MediaTypes;
import com.controle.estoque.controledeestoque.file.exporter.contract.FileExporter;
import com.controle.estoque.controledeestoque.file.exporter.impl.CsvExporter;
import com.controle.estoque.controledeestoque.file.exporter.impl.PdfExporter;
import com.controle.estoque.controledeestoque.file.exporter.impl.XlsxExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FileExporterFactory {
    private static Logger logger = LoggerFactory.getLogger(FileExporterFactory.class);

    @Autowired
    private ApplicationContext context;




    public FileExporter getExporter(String acceptHeader) throws Exception {
        if (acceptHeader.contains(MediaTypes.APPLICATION_XLSX_VALUE)) {


            return context.getBean(XlsxExporter.class);


        }
        else if (acceptHeader.contains(MediaTypes.APPLICATION_CSV)) {

            return context.getBean(CsvExporter.class);
        }else if (acceptHeader.contains(MediaTypes.APPLICATION_PDF)) {

            return context.getBean(PdfExporter.class);
        }
        else {
            throw new BadRequestException("arquivo com formato invalido");
        }

    }

}
