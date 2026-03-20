package com.controle.estoque.controledeestoque.DTO;

import com.controle.estoque.controledeestoque.model.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record RequestDTO(@NotBlank(message = "Nome obrigatorio") String nome,
                         @NotNull(message = "preco obrigatorio") BigDecimal preco,
                         @NotNull(message = "Quantidade obrigatoria") Integer quantidade,
                         @NotNull(message = "Categoria obrigatorio") Categoria categoria,
                         @NotNull(message = "Fornecedor obrigatorio")List<Long> fornecedorId) {
}
