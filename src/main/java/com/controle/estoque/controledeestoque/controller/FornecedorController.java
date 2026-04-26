package com.controle.estoque.controledeestoque.controller;


import com.controle.estoque.controledeestoque.DTO.RequestFornecedorDTo;
import com.controle.estoque.controledeestoque.DTO.ResponseFornecedorDTO;
import com.controle.estoque.controledeestoque.service.FornecedorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.controle.estoque.controledeestoque.annotation.swagger.FornecedorSwagger;



@Tag(name = "Fornecedores", description = "Endpoints para gerenciamento de Fornecedores")
@RestController
@RequestMapping("/fornecedores")

public class FornecedorController {

    FornecedorService  fornecedorService;


    @Autowired
    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }


    @PostMapping
    @FornecedorSwagger.SwaggerCreateFornecedor
    public ResponseEntity<ResponseFornecedorDTO> criar(@RequestBody @Valid RequestFornecedorDTo DTo) {
        ResponseFornecedorDTO criar = fornecedorService.criar(DTo);
        return ResponseEntity.status(HttpStatus.CREATED).body(criar);

    }

    @GetMapping
    @FornecedorSwagger.SwaggerGetAllFornecedores
    public ResponseEntity<Page<ResponseFornecedorDTO>> listar(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size,
                                                              @RequestParam(defaultValue = "id") String ordem) {

        Pageable pageRequest = PageRequest.of(page, size, Sort.by(ordem).ascending());
        return ResponseEntity.ok(fornecedorService.listarTodos(pageRequest));
    }



    @GetMapping("/{id}")
    @FornecedorSwagger.SwaggerFindIdFornecedor
    public ResponseEntity<ResponseFornecedorDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(fornecedorService.buscarPorId(id));
    }


    @PutMapping("/{id}")
    @FornecedorSwagger.SwaggerUpdateFornecedor
    public ResponseEntity<ResponseFornecedorDTO> atualizar(@PathVariable  Long id, @RequestBody @Valid RequestFornecedorDTo DTo) {
        return ResponseEntity.ok(fornecedorService.atualizar(id,DTo));
    }


    @DeleteMapping("/{id}")
    @FornecedorSwagger.SwaggerDeleteFornecedor
    public ResponseEntity<ResponseFornecedorDTO> deletar(@PathVariable Long id) {
        fornecedorService.deletar(id);
        return ResponseEntity.noContent().build();

    }


    @GetMapping("/{fornecedorId}/produto")
    @FornecedorSwagger.SwaggerlistarProdutosPorFornecedor
    public ResponseEntity<ResponseFornecedorDTO> listarProdutosPorFornecedor(@PathVariable Long fornecedorId) {
        return ResponseEntity.ok(fornecedorService.listarProdutosPorFornecedor(fornecedorId));
    }

    @DeleteMapping("/{fornecedorId}/{produtoId}")
    @FornecedorSwagger.SwaggerdeletarProdutoPorFornecedor
    public ResponseEntity<ResponseFornecedorDTO> deletarProdutoPorFornecedor(@PathVariable Long produtoId,
                                                                             @PathVariable Long fornecedorId) {
        return ResponseEntity.ok(fornecedorService.removerProduto(produtoId, fornecedorId));
    }

    @PostMapping("/{fornecedorId}/produtos/{produtoId}")
    @FornecedorSwagger.SwaggeradicionarProdutoAoFornecedor
    public ResponseEntity<ResponseFornecedorDTO> adicionarProdutoAoFornecedor(@PathVariable Long produtoId,
                                                                              @PathVariable Long fornecedorId) {
        return ResponseEntity.ok(fornecedorService.adicionarProduto(produtoId, fornecedorId));
    }








    @PatchMapping("/{fornecedorId}/desativar")
    @FornecedorSwagger.SwaggerdesabilitarFornecedor
    public ResponseEntity<Void> desativarFornecedor(@PathVariable("fornecedorId") Long fornecedorId) {
        ResponseFornecedorDTO fornecedorDTo = fornecedorService.desativarFornecedor(fornecedorId);
        return ResponseEntity.noContent().build();

    }









}
