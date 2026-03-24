package com.controle.estoque.controledeestoque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class ControledeestoqueApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControledeestoqueApplication.class, args);
	}

}
