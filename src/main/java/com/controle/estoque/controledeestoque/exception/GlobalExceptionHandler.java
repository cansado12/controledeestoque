package com.controle.estoque.controledeestoque.exception;

import com.controle.estoque.controledeestoque.DTO.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProdutoNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleProdutoNotFound(ProdutoNotFoundException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                404, "Não Encontrado", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EstoqueInsuficienteException.class)
    public ResponseEntity<ErrorResponseDTO> handleEstoqueInsuficiente(EstoqueInsuficienteException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                400, "Estoque Insuficiente", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ProdutoInvalidoException.class)
    public ResponseEntity<ErrorResponseDTO> handleProdutoInvalido(ProdutoInvalidoException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                400, "Dados Inválidos", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidacao(MethodArgumentNotValidException ex) {
        String mensagem = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponseDTO error = new ErrorResponseDTO(
                400, "Erro de Validação", mensagem
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleErroGenerico(Exception ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                500, "Erro Interno", "Ocorreu um erro inesperado."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
    @ExceptionHandler(FornecedorNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleFornecedorNotFound(FornecedorNotFoundException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                404, "Não Encontrado", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}