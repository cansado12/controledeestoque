package com.controle.estoque.controledeestoque.controller;

import com.controle.estoque.controledeestoque.DTO.RequestDTO;
import com.controle.estoque.controledeestoque.DTO.ResponseDTO;
import com.controle.estoque.controledeestoque.service.ProdutoService;
import com.controle.estoque.controledeestoque.model.Categoria;
import com.controle.estoque.controledeestoque.swagger.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "produtos", description = "Endpoints para gerenciamento de produtos")
@RestController
@RequestMapping("/produto")
public class ProdutoController {

    ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }





    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})


    @ProdutoSwagger.SwaggerGetAllProdutos
    public ResponseEntity<Page<ResponseDTO>> findAll(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size,
                                                     @RequestParam(defaultValue = "id") String sort) {

        Pageable pageRequest = PageRequest.of(page, size, Sort.by(sort).ascending());
        return ResponseEntity.ok(produtoService.findAll(pageRequest));
    }




    @GetMapping(value = "/{id}",  produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})

    @ProdutoSwagger.SwaggerFindIdProduto
    public ResponseEntity<ResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(produtoService.findById(id));
    }

    @ProdutoSwagger.SwaggerCreateProduce
    @PostMapping
    public ResponseEntity<ResponseDTO> create(@RequestBody @Valid RequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.create(requestDTO));

    }

    @ProdutoSwagger.SwaggerUpdateProduto
    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})

    public ResponseEntity<ResponseDTO> update(@PathVariable Long id, @RequestBody @Valid RequestDTO requestDTO){

        ResponseDTO update = produtoService.update(id, requestDTO);
        return ResponseEntity.ok(update);

    }


    @ProdutoSwagger.SwaggerDeleteProduto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        produtoService.delete(id);
        return ResponseEntity.noContent().build();

    }


    @ProdutoSwagger.SwaggerGetByCategoria
    @GetMapping(value = "/categoria/{categoria}",  produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    public ResponseEntity<Page<ResponseDTO>> findByCategoria(@PathVariable Categoria categoria,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "id") String sort){
        Pageable pageRequest = PageRequest.of(page, 10, Sort.by(sort));
        return ResponseEntity.ok(produtoService.findByCategoria(categoria,pageRequest));

    }




















}
