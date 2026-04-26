package com.controle.estoque.controledeestoque.controller;


import com.controle.estoque.controledeestoque.DTO.UploadFileResponseDTO;
import com.controle.estoque.controledeestoque.exception.FileStorageException;
import com.controle.estoque.controledeestoque.service.FileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Tag(name = "arquivos", description = "Endpoints para gerenciamento de arquivos")
@RestController
@RequestMapping("/api/file/v1")
public class FileStorageController {

    FileStorageService service;
    private static final Logger logger = LoggerFactory.getLogger(FileStorageController.class);
    public FileStorageController(FileStorageService service) {
        this.service = service;
    }



    @PostMapping("/uploadFile")
    public UploadFileResponseDTO upload(@RequestParam("file") MultipartFile file) {
        var fileName = service.storeFile(file);
        var fileDowloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/file/v1/download/")
                .path(fileName).toUriString();
        UploadFileResponseDTO responseDTO = new UploadFileResponseDTO(fileName, fileDowloadUri, file.getContentType(), file.getSize());
        return  responseDTO;




    }


    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponseDTO> uploadMultipleFiles(@RequestParam("files") List<MultipartFile> files) {
        return files.stream().map(this::upload).toList();
    }



    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = service.loadFileAsResource(fileName);
        String contentType = null;

        try {

            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        } catch (Exception e) {
            logger.error("Erro ao acessar o arquivo " + fileName, e);
            throw new FileStorageException("Erro ao acessar o arquivo " + fileName, e);
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType)).
                header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);





    }




}
