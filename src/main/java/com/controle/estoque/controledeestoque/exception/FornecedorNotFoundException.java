package com.controle.estoque.controledeestoque.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FornecedorNotFoundException extends RuntimeException {
    public FornecedorNotFoundException(Long id) {
        super("Fornecedor com ID: " + id + " nao encontrado");
    }
}
