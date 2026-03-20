package com.controle.estoque.controledeestoque.DTO;

import com.controle.estoque.controledeestoque.model.Categoria;

import java.math.BigDecimal;

public record ResponseDTO(Long id, String nome, BigDecimal preco, Integer quantidade, Categoria categoria) {
}
