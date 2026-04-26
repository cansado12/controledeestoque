package com.controle.estoque.controledeestoque.controller;

import com.controle.estoque.controledeestoque.DTO.ProdutoDTo;
import com.controle.estoque.controledeestoque.DTO.RequestDTO;
import com.controle.estoque.controledeestoque.DTO.ResponseDTO;
import com.controle.estoque.controledeestoque.annotation.swagger.ProdutoSwagger;
import com.controle.estoque.controledeestoque.exception.FileStorageException;
import com.controle.estoque.controledeestoque.file.exporter.MediaTypes;
import com.controle.estoque.controledeestoque.service.ProdutoService;
import com.controle.estoque.controledeestoque.model.Categoria;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Tag(name = "produtos", description = "Endpoints para gerenciamento de produtos")
@RestController
@RequestMapping("/produtos")
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


    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})

    @ProdutoSwagger.SwaggerFindIdProduto
    public ResponseEntity<ResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.findById(id));
    }

    @ProdutoSwagger.SwaggerCreateProduce
    @PostMapping
    public ResponseEntity<ResponseDTO> create(@RequestBody @Valid RequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.create(requestDTO));

    }

    @ProdutoSwagger.SwaggerUpdateProduto
    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})

    public ResponseEntity<ResponseDTO> update(@PathVariable Long id, @RequestBody @Valid RequestDTO requestDTO) {

        ResponseDTO update = produtoService.update(id, requestDTO);
        return ResponseEntity.ok(update);

    }


    @ProdutoSwagger.SwaggerDeleteProduto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();

    }


    @ProdutoSwagger.SwaggerGetByCategoria
    @GetMapping(value = "/categoria/{categoria}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    public ResponseEntity<Page<ResponseDTO>> findByCategoria(@PathVariable Categoria categoria,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "id") String sort) {
        Pageable pageRequest = PageRequest.of(page, 10, Sort.by(sort));
        return ResponseEntity.ok(produtoService.findByCategoria(categoria, pageRequest));

    }


    @ProdutoSwagger.SwaggerUploadProduce
    @PostMapping(value = "/massCreation", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ProdutoDTo>> massCreation(
            @RequestParam("file") MultipartFile file) throws Exception {
        List<ProdutoDTo> produtos = produtoService.massCreation(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtos);
    }


    @ProdutoSwagger.SwaggerExportProduce
    @GetMapping("/exportPage")
    public ResponseEntity<Resource> exportPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestHeader(value = HttpHeaders.ACCEPT, required = false) String acceptHeader) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());

        List<MediaType> acceptedTypes = MediaType.parseMediaTypes(
                acceptHeader != null ? acceptHeader : MediaType.ALL_VALUE
        );

        MediaType mediaType = resolveMediaType(acceptedTypes);

        Resource file = produtoService.exportPage(pageable, mediaType.toString());

        String extension = switch (mediaType.toString()) {
            case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" -> "xlsx";
            case "text/csv" -> "csv";

            case "application/pdf" -> "pdf";
            default -> "bin";
        };

        String fileName = "produto_export." + extension;

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(file);
    }

    private MediaType resolveMediaType(List<MediaType> acceptedTypes) {
        List<MediaType> supported = List.of(
                MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
                MediaType.parseMediaType("text/csv"),
                MediaType.APPLICATION_PDF
        );

        return acceptedTypes.stream()
                .flatMap(accepted -> supported.stream()
                        .filter(supportedType -> accepted.isCompatibleWith(supportedType)))
                .findFirst()
                .orElse(MediaType.APPLICATION_OCTET_STREAM);
    }

}













