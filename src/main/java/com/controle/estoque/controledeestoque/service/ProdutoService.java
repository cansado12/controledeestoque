package com.controle.estoque.controledeestoque.service;


import com.controle.estoque.controledeestoque.DTO.RequestDTO;
import com.controle.estoque.controledeestoque.DTO.ResponseDTO;
import com.controle.estoque.controledeestoque.exception.FornecedorNotFoundException;
import com.controle.estoque.controledeestoque.exception.ProdutoNotFoundException;
import com.controle.estoque.controledeestoque.mapper.custom.ProdutoMapper;
import com.controle.estoque.controledeestoque.model.Categoria;
import com.controle.estoque.controledeestoque.model.Produto;
import com.controle.estoque.controledeestoque.repository.ProdutoRepository;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    ProdutoMapper produtoMapper;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ProdutoService.class);
    ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoMapper produtoMapper, ProdutoRepository produtoRepository) {
        this.produtoMapper = produtoMapper;
        this.produtoRepository = produtoRepository;
    }

    public Page<ResponseDTO> findAll(Pageable pageable) {
        return  produtoRepository.findAll(pageable).map(produtoMapper::toDTO);
    }


    public ResponseDTO findById(Long id) {
        return  produtoRepository
                .findById(id).
                map(produtoMapper::toDTO).
                orElseThrow(() -> new ProdutoNotFoundException(id));

    }

    public ResponseDTO update(Long id, RequestDTO produto) {
        Produto p = produtoRepository.findById(id).orElseThrow(() -> new ProdutoNotFoundException(id));
        p.setNome(produto.nome());
        p.setPreco(produto.preco());
        p.setQuantidade(produto.quantidade());
        p.setCategoria(produto.categoria());

        var save = produtoRepository.save(p);
        return produtoMapper.toDTO(save);
    }

    public void delete(Long id) {
        produtoRepository.findById(id).orElseThrow(() -> new ProdutoNotFoundException(id));
        produtoRepository.deleteById(id);


    }

    public ResponseDTO create(RequestDTO produto) {
        Produto entity = produtoMapper.toEntity(produto);

        if (entity.getCategoria() == null) {
            entity.setCategoria(Categoria.OUTROS);

        }
        entity = produtoRepository.save(entity);
        return produtoMapper.toDTO(entity);

    }

    public Page<ResponseDTO> findByCategoria(Categoria categoria, Pageable pageable) {
        return  produtoRepository.findByCategoria(categoria, pageable).map(produtoMapper::toDTO);

    }


}
