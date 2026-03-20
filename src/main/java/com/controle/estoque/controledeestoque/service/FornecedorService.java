package com.controle.estoque.controledeestoque.service;


import com.controle.estoque.controledeestoque.DTO.ProdutoDTo;
import com.controle.estoque.controledeestoque.DTO.RequestFornecedorDTo;
import com.controle.estoque.controledeestoque.DTO.ResponseFornecedorDTo;
import com.controle.estoque.controledeestoque.exception.FornecedorNotFoundException;
import com.controle.estoque.controledeestoque.exception.ProdutoNotFoundException;
import com.controle.estoque.controledeestoque.mapper.custom.FornecedorMapper;
import com.controle.estoque.controledeestoque.model.Fornecedor;
import com.controle.estoque.controledeestoque.model.Produto;
import com.controle.estoque.controledeestoque.repository.FornecedorRepository;
import com.controle.estoque.controledeestoque.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FornecedorService {

    FornecedorRepository fornecedorRepository;
    FornecedorMapper mapper;

    ProdutoRepository produtoRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository, FornecedorMapper mapper, ProdutoRepository produtoRepository) {
        this.fornecedorRepository = fornecedorRepository;
        this.mapper = mapper;

        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public ResponseFornecedorDTo criar(RequestFornecedorDTo dto) {
        Fornecedor entity = mapper.toEntity(dto);
        entity = fornecedorRepository.save(entity);
        return mapper.toDTO(entity);


    }
    @Transactional(readOnly = true)
    public ResponseFornecedorDTo buscarPorId(Long id) {
     return fornecedorRepository.findById(id).map(mapper::toDTO).
                orElseThrow(() -> new FornecedorNotFoundException(id));

    }

    @Transactional(readOnly = true)
    public Page<ResponseFornecedorDTo> listarTodos(Pageable pageable) {

        return fornecedorRepository.findAll(pageable).map(mapper::toDTO);
    }

    @Transactional
    public ResponseFornecedorDTo atualizar(Long id, RequestFornecedorDTo dto) {
        Fornecedor f = fornecedorRepository.findById(id).orElseThrow(() -> new FornecedorNotFoundException(id));
        f.setNome(dto.nome());
        f.setCnpj(dto.cnpj());
        f.setEmail(dto.email());
        f.setTelefone(dto.telefone());

       var save =   fornecedorRepository.save(f);
       return mapper.toDTO(save);

    }


    @Transactional
    public void deletar(Long id) {
        if (fornecedorRepository.findById(id).isEmpty()) {
            throw new FornecedorNotFoundException(id);
        }
        fornecedorRepository.deleteById(id);
    }


    @Transactional
    public ResponseFornecedorDTo adicionarProduto(Long produtoId, Long fornecedorId) {
        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId).orElseThrow(() -> new FornecedorNotFoundException(fornecedorId));
        Produto produto = produtoRepository.findById(produtoId).orElseThrow(() -> new ProdutoNotFoundException(produtoId));

        fornecedor.getProdutos().add(produto);
        fornecedorRepository.save(fornecedor);
        return mapper.toDTO(fornecedor);

    }

    @Transactional
    public ResponseFornecedorDTo removerProduto(Long produtoId, Long fornecedorId){
        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId).orElseThrow(() -> new FornecedorNotFoundException(fornecedorId));
        Produto produto = produtoRepository.findById(produtoId).orElseThrow(() -> new ProdutoNotFoundException(produtoId));

        fornecedor.getProdutos().remove(produto);
        fornecedorRepository.save(fornecedor);
        return mapper.toDTO(fornecedor);
    }

    @Transactional(readOnly = true)
    public ResponseFornecedorDTo listarProdutosPorFornecedor(Long fornecedorId) {
        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId)
                .orElseThrow(() -> new FornecedorNotFoundException(fornecedorId));



       return mapper.toDTO(fornecedor);
    }







}
