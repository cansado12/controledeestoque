package com.controle.estoque.controledeestoque.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FornecedorDto(@NotBlank(message = "Nome do fornecedor obrigatorio") String nome,
                            @NotNull(message = "Id obrigatorio") Long id) {
}
