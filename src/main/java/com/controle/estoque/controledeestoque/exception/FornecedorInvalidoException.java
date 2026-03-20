package com.controle.estoque.controledeestoque.exception;

public class FornecedorInvalidoException extends RuntimeException {
    public FornecedorInvalidoException(String fornecedor) {
        super("Dados do fornecedor invalido: " + fornecedor);
    }
}
