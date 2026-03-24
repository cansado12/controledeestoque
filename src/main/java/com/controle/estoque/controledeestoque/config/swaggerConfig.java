package com.controle.estoque.controledeestoque.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;


@OpenAPIDefinition(
        info = @Info(
                title = "API Controle de Estoque",
                version = "1.0",
                description = "API para gestão de produtos e fornecedores"
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Ambiente local")
        }
)

public class swaggerConfig {


}
