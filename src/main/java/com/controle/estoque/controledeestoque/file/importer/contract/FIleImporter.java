package com.controle.estoque.controledeestoque.file.importer.contract;

import com.controle.estoque.controledeestoque.DTO.ProdutoDTo;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface FIleImporter {


    List<ProdutoDTo> importFile(InputStream stream) throws IOException;
}
