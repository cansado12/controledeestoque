package com.controle.estoque.controledeestoque.DTO;

import com.controle.estoque.controledeestoque.model.Fornecedor;

import java.util.List;

public record ResponseFornecedorDTo(Long id
        ,String nome
        , String cnpj
        , String email
        , String telefone
        , List<ProdutoDTo> produtos
        , boolean ativo) {
}
