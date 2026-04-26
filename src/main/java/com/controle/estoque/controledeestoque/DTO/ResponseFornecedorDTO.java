package com.controle.estoque.controledeestoque.DTO;

import java.util.List;

public record ResponseFornecedorDTO(Long id
        , String nome
        , String cnpj
        , String email
        , String telefone
        , List<ProdutoDTo> produtos
        , boolean ativo) {
}
