package com.controle.estoque.controledeestoque.DTO;

import com.controle.estoque.controledeestoque.model.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProdutoDTo(@NotNull(message = "Id obrigatorio") Long id,
                         @NotBlank(message = "Nome do produto obrigatorio") String nome,
                         @NotNull(message = "Preco obrigatorio") BigDecimal preco,
                         @NotNull(message = "Quantidade obrigatoria") Integer quantidade,
                         @NotNull(message = "Categoria obrigatoria") Categoria categoria
)

{


}
