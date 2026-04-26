package com.controle.estoque.controledeestoque.annotation.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class ProdutoSwagger {
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)

    @Operation(
            summary = "deletar o produto",
            description = "deletar as informaçoes de um produto"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Pedido invalido"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public @interface SwaggerDeleteProduto {
    }


    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Cria um produto",
            description = "Cria e adiciona um produto"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto cadstrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Pedido invalido"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })

    public @interface SwaggerCreateProduce {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)

    @Operation(
            summary = "Lista produto por id",
            description = "Retorna o produto com o id selecionado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Pedido invalido"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")})

    public @interface SwaggerFindIdProduto {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Lista todas os produtos",
            description = "Retorna uma lista paginada de todos os produtos cadastradas"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produtos encontradas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Pedido invalido"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })

    public @interface SwaggerGetAllProdutos {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)

    @Operation(
            summary = "Filtra produtos",
            description = "Filtra todas os produtos por categoria desejada"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produtos encontrados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Pedido invalido"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")})

    public @interface SwaggerGetByCategoria {
    }
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Criacao massiva de produto",
        description = "Criacao massiva de produto com upload de de XLSX ou CSV"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produtos criados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Pedido invalido"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")})

    public @interface SwaggerUpdateProduto {
    }


    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Cria um produto",
            description = "Cria e adiciona um produto"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto cadstrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Pedido invalido"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })

    public @interface SwaggerUploadProduce {
    }


    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "exporta produtos",
            description = "exporta produtos enviados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto cadstrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Pedido invalido"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })

    public @interface SwaggerExportProduce {
    }



}
