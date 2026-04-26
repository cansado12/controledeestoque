package com.controle.estoque.controledeestoque.service;


import com.controle.estoque.controledeestoque.DTO.ProdutoDTo;
import com.controle.estoque.controledeestoque.DTO.RequestDTO;
import com.controle.estoque.controledeestoque.DTO.ResponseDTO;
import com.controle.estoque.controledeestoque.exception.BadRequestException;
import com.controle.estoque.controledeestoque.exception.ProdutoNotFoundException;
import com.controle.estoque.controledeestoque.file.exporter.contract.FileExporter;
import com.controle.estoque.controledeestoque.file.exporter.factory.FileExporterFactory;
import com.controle.estoque.controledeestoque.file.importer.contract.FIleImporter;
import com.controle.estoque.controledeestoque.file.importer.factory.FileImporterFactory;
import com.controle.estoque.controledeestoque.mapper.custom.ProdutoMapper;
import com.controle.estoque.controledeestoque.model.Categoria;
import com.controle.estoque.controledeestoque.model.Produto;
import com.controle.estoque.controledeestoque.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    private final ProdutoMapper produtoMapper;


    private final FileImporterFactory importer;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ProdutoService.class);
    private final ProdutoRepository produtoRepository;
    private final FileExporterFactory fileExporterFactory;

    public ProdutoService(ProdutoMapper produtoMapper, FileImporterFactory importer, ProdutoRepository produtoRepository, FileExporterFactory fileExporterFactory) {
        this.produtoMapper = produtoMapper;
        this.importer = importer;
        this.produtoRepository = produtoRepository;
        this.fileExporterFactory = fileExporterFactory;
    }

    @Transactional(readOnly = true)
    public Page<ResponseDTO> findAll(Pageable pageable) {
        logger.info("Buscando todos os produtos");
        return  produtoRepository.findAll(pageable).map(produtoMapper::toDTO);
    }





    @Transactional(readOnly = true)
    public ResponseDTO findById(Long id) {
        return  produtoRepository
                .findById(id).
                map(produtoMapper::toDTO).
                orElseThrow(() -> new ProdutoNotFoundException(id));

    }

    @Transactional
    public ResponseDTO update(Long id, RequestDTO produto) {
        Produto p = produtoRepository.findById(id).orElseThrow(() -> new ProdutoNotFoundException(id));
        p.setNome(produto.nome());
        p.setPreco(produto.preco());
        p.setQuantidade(produto.quantidade());
        p.setCategoria(produto.categoria());


        var save = produtoRepository.save(p);
        return produtoMapper.toDTO(save);
    }

    @Transactional
    public void delete(Long id) {
        produtoRepository.findById(id).orElseThrow(() -> new ProdutoNotFoundException(id));
        produtoRepository.deleteById(id);


    }

    @Transactional
    public ResponseDTO create(RequestDTO produto) {
        Produto entity = produtoMapper.toEntity(produto);

        if (entity.getCategoria() == null) {
            entity.setCategoria(Categoria.OUTROS);

        }
        entity = produtoRepository.save(entity);
        return produtoMapper.toDTO(entity);

    }

    @Transactional(readOnly = true)
    public Page<ResponseDTO> findByCategoria(Categoria categoria, Pageable pageable) {
        return  produtoRepository.findByCategoria(categoria, pageable).map(produtoMapper::toDTO);



    }





    @Transactional
    public List<ProdutoDTo> massCreation(MultipartFile file) throws Exception {

        logger.info("importando produtos de fornecedores");

        if (file.isEmpty()) {
            throw new BadRequestException("Arquivo vazio");
        }

        try (InputStream inputStream = file.getInputStream()) {

            String fileName = Optional.ofNullable(file.getOriginalFilename())
                    .orElseThrow(() -> new BadRequestException("Nome do arquivo inválido"));

            FIleImporter importer = this.importer.getImporter(fileName);

            ObjectMapper mapper = new ObjectMapper();

            List<Produto> produtos = new ArrayList<>();

            for (Object dto : importer.importFile(inputStream)) {

                Produto produto = mapper.convertValue(dto, Produto.class);


                if (produto.getNome() == null) {
                    throw new BadRequestException("Produto sem nome");
                }

                produtos.add(produto);
            }

            List<Produto> saved = produtoRepository.saveAll(produtos);


            return saved.stream()
                    .map(produtoMapper::toProdutoDTO)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo", e);
        }
    }


    @Transactional(readOnly = true)
    public Resource exportPage(Pageable pageable, String acceptHeader) {
        logger.info("Exportando produtos");

        var produtos = produtoRepository.findAll(pageable)
                .map(produtoMapper::toProdutoDTO)
                .getContent();

        try {
            FileExporter exporter = this.fileExporterFactory.getExporter(acceptHeader);
            return exporter.exportFile(produtos);
        } catch (Exception e) {
            throw new RuntimeException("Erro na exportação de arquivo", e);
        }
    }








}
