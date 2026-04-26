package com.controle.estoque.controledeestoque.service;

import com.controle.estoque.controledeestoque.config.FileStorageConfig;
import com.controle.estoque.controledeestoque.exception.FileNotFoundException;
import com.controle.estoque.controledeestoque.exception.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    private static final Logger LOG = LoggerFactory.getLogger(FileStorageService.class);
    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        Path path = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath()
                .normalize();


        this.fileStorageLocation = path;
        try {
            LOG.info("Criando o arquivo {}", fileStorageLocation);
            Files.createDirectories(this.fileStorageLocation);

        } catch (Exception e) {
            LOG.error("Diretorio nao pode ser criado");
            throw new FileStorageException("Diretorio nao pode ser criado", e);
        }




    }


    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Desculpe, nome do arquivo é invalido " +  fileName);

            }
            LOG.info("Criando o arquivo {}", fileName);
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;



        } catch (Exception e) {
            LOG.error("Erro ao salvar o arquivo {}, Tente novamente", file);
            throw new FileStorageException("Erro ao salvar o arquivo " + file + ", Tente novamente", e);
        }
    }


    public Resource loadFileAsResource(String fileName) {
        if (fileName.contains("..")) {
            throw new FileStorageException("Nome de arquivo inválido: " + fileName);
        }

        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            }
            LOG.error("Arquivo nao encontrado {}", fileName);
            throw new FileNotFoundException("Arquivo nao encontrado " + fileName);

        } catch (java.net.MalformedURLException e) {
            throw new FileNotFoundException("Erro ao carregar o arquivo " + fileName, e);
        }
    }



}
