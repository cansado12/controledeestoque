package com.controle.estoque.controledeestoque.controller;


import com.controle.estoque.controledeestoque.DTO.RequestFornecedorDTo;
import com.controle.estoque.controledeestoque.DTO.ResponseFornecedorDTo;
import com.controle.estoque.controledeestoque.service.FornecedorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.controle.estoque.controledeestoque.annotation.swagger.FornecedorSwagger;



@Tag(name = "Fornecedores", description = "Endpoints para gerenciamento de Fornecedores")
@RestController
@RequestMapping("/fornecedor")

public class FornecedorController {

    FornecedorService  fornecedorService;


    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }


    @PostMapping
    @FornecedorSwagger.SwaggerCreateFornecedor
    public ResponseEntity<ResponseFornecedorDTo> criar(@RequestBody @Valid RequestFornecedorDTo DTo) {
        ResponseFornecedorDTo criar = fornecedorService.criar(DTo);
        return ResponseEntity.ok().body(criar);

    }

    @GetMapping
    @FornecedorSwagger.SwaggerGetAllFornecedores
    public ResponseEntity<Page<ResponseFornecedorDTo>> listar(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size,
                                                              @RequestParam(defaultValue = "id") String ordem) {

        Pageable pageRequest = PageRequest.of(page, size, Sort.by(ordem).ascending());
        return ResponseEntity.ok(fornecedorService.listarTodos(pageRequest));
    }


    @GetMapping("/{id}")
    @FornecedorSwagger.SwaggerFindIdFornecedor
    public ResponseEntity<ResponseFornecedorDTo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(fornecedorService.buscarPorId(id));
    }


    @PutMapping("/{id}")
    @FornecedorSwagger.SwaggerUpdateFornecedor
    public ResponseEntity<ResponseFornecedorDTo> atualizar(@PathVariable  Long id, @RequestBody @Valid RequestFornecedorDTo DTo) {
        return ResponseEntity.ok(fornecedorService.atualizar(id,DTo));
    }


    @DeleteMapping("/{id}")
    @FornecedorSwagger.SwaggerDeleteFornecedor
    public ResponseEntity<ResponseFornecedorDTo> deletar(@PathVariable Long id) {
        fornecedorService.deletar(id);
        return ResponseEntity.noContent().build();

    }


    @GetMapping("/{fornecedorId}/produto")
    @FornecedorSwagger.SwaggerlistarProdutosPorFornecedor
    public ResponseEntity<ResponseFornecedorDTo> listarProdutosPorFornecedor(@PathVariable Long fornecedorId) {
        return ResponseEntity.ok(fornecedorService.listarProdutosPorFornecedor(fornecedorId));
    }

    @DeleteMapping("/{fornecedor}/{produtoId}")
    @FornecedorSwagger.SwaggerdeletarProdutoPorFornecedor
    public ResponseEntity<ResponseFornecedorDTo> deletarProdutoPorFornecedor(@PathVariable Long produtoId,
                                                                             @PathVariable Long fornecedorId) {
        return ResponseEntity.ok(fornecedorService.removerProduto(produtoId, fornecedorId));
    }

    @PutMapping("/{fornecedor}/{produtoId}")
    @FornecedorSwagger.SwaggeradicionarProdutoAoFornecedor
    public ResponseEntity<ResponseFornecedorDTo> adicionarProdutoAoFornecedor(@PathVariable Long produtoId,
                                                                             @PathVariable Long fornecedorId) {
        return ResponseEntity.ok(fornecedorService.adicionarProduto(produtoId, fornecedorId));
    }















}
