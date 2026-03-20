package com.controle.estoque.controledeestoque.exception;

public class ProdutoInvalidoException extends RuntimeException {
    public ProdutoInvalidoException(String produto) {
        super("O produto Solicitado é invalido: " + produto);
    }
}
