package com.controle.estoque.controledeestoque.exception;

import com.controle.estoque.controledeestoque.DTO.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProdutoNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleProdutoNotFound(ProdutoNotFoundException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(404, "Nao Encontrado", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EstoqueInsuficienteException.class)
    public ResponseEntity<ErrorResponseDTO> handleEstoqueInsuficiente(EstoqueInsuficienteException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(400, "Estoque Insuficiente", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ProdutoInvalidoException.class)
    public ResponseEntity<ErrorResponseDTO> handleProdutoInvalido(ProdutoInvalidoException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(400, "Dados Invalidos", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidacao(MethodArgumentNotValidException ex) {
        String mensagem = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponseDTO error = new ErrorResponseDTO(400, "Erro de Validacao", mensagem);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleErroGenerico(Exception ex, HttpServletRequest request) {
        if (acceptsBinaryContent(request)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        ErrorResponseDTO error = new ErrorResponseDTO(
                500,
                "Erro Interno",
                ex.getMessage() != null ? ex.getMessage() : "Ocorreu um erro inesperado."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(FornecedorNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleFornecedorNotFound(FornecedorNotFoundException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(404, "Nao Encontrado", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(FornecedorInvalidoException.class)
    public ResponseEntity<ErrorResponseDTO> handleFornecedorInvalido(FornecedorInvalidoException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(409, "Fornecedor invalido", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleFileNotFound(FileNotFoundException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(404, "Arquivo nao encontrado", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ErrorResponseDTO> handleFileStorage(FileStorageException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(500, "Erro Interno", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadrequest(BadRequestException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(400, "Requisicao invalida", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String mensagem = "Valor invalido para o parametro '" + ex.getName() + "': " + ex.getValue();
        ErrorResponseDTO error = new ErrorResponseDTO(400, "Parametro invalido", mensagem);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    private boolean acceptsBinaryContent(HttpServletRequest request) {
        String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);
        if (acceptHeader == null || acceptHeader.isBlank()) {
            return false;
        }

        List<MediaType> acceptedTypes = MediaType.parseMediaTypes(acceptHeader);
        List<MediaType> binaryTypes = List.of(
                MediaType.APPLICATION_PDF,
                MediaType.parseMediaType("text/csv"),
                MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
                MediaType.APPLICATION_OCTET_STREAM
        );

        return acceptedTypes.stream()
                .anyMatch(accepted -> binaryTypes.stream().anyMatch(accepted::isCompatibleWith));
    }
}
