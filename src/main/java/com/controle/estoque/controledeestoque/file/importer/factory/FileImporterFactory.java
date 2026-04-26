package com.controle.estoque.controledeestoque.file.importer.factory;


import com.controle.estoque.controledeestoque.exception.BadRequestException;
import com.controle.estoque.controledeestoque.file.importer.contract.FIleImporter;
import com.controle.estoque.controledeestoque.file.importer.impl.CsvImporter;
import com.controle.estoque.controledeestoque.file.importer.impl.XlsxImporter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FileImporterFactory {
    private static Logger logger = LoggerFactory.getLogger(FileImporterFactory.class);

    @Autowired
    private ApplicationContext context;




    public FIleImporter getImporter(String fileName) throws Exception {
        if (fileName.endsWith(".xlsx")) {
//            return new XlsxImporter();

            return context.getBean(XlsxImporter.class);


        }
        else if (fileName.endsWith(".csv")) {
//            return  new CsvImporter();

            return context.getBean(CsvImporter.class);
        }
        else {
            throw new BadRequestException("arquivo com formato invalido");
        }

    }

}
