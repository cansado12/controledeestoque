package com.controle.estoque.controledeestoque.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record RequestFornecedorDTo(@NotBlank(message = "Nome obrigatorio") String nome,
                                   @NotBlank(message = "Cnpj obrigatorio")
                                   @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter 14 dígitos") String cnpj,
                                   @NotBlank(message = "Email obrigatorio")
                                   @Email(message = "Email invalido") String email,
                                   @NotBlank(message = "Telefone obrigatorio") String telefone,
                                   List<Long> produtosId) {

}
