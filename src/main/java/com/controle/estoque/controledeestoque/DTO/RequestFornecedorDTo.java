package com.controle.estoque.controledeestoque.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RequestFornecedorDTo(@NotBlank(message = "Nome obrigatorio") String nome,
                                   @NotBlank(message = "Cnpj obrigatorio") String cnpj,
                                   @NotBlank(message = "Email obrigatorio") String email,
                                   @NotBlank(message = "Telefone obrigatorio") String telefone,
                                   List<Long> produtosId) {

}
