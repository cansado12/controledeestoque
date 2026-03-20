package com.controle.estoque.controledeestoque.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EstoqueInsuficienteException extends RuntimeException {
    public EstoqueInsuficienteException(Integer quantidadeDisponivel, Integer quantidadeSolicitante) {

        super("Estoque insuficiente. Disponivel: " + quantidadeDisponivel + ", Solicitante: " + quantidadeSolicitante);
    }
}
