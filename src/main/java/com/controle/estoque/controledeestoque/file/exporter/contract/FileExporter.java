package com.controle.estoque.controledeestoque.file.exporter.contract;

import com.controle.estoque.controledeestoque.DTO.ProdutoDTo;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public interface FileExporter {

   Resource exportFile(List<ProdutoDTo> produtos) throws IOException, JRException;
}