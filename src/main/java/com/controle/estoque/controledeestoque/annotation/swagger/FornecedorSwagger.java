package com.controle.estoque.controledeestoque.annotation.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class FornecedorSwagger {



        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)

        @Operation(
                summary = "deletar o fornecedor",
                description = "deletar as informaçoes de um fornecedor"
        )
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Fornecedor deletado com sucesso"),
                @ApiResponse(responseCode = "400", description = "Pedido invalido"),
                @ApiResponse(responseCode = "401", description = "Não autorizado"),
                @ApiResponse(responseCode = "404", description = "Não encontrado"),
                @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        })
        public @interface SwaggerDeleteFornecedor {
        }


        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @Operation(
                summary = "Cria um fornecedor",
                description = "Cria e adiciona um fornecedor"
        )
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Fornecedor cadastrado com sucesso"),
                @ApiResponse(responseCode = "400", description = "Pedido invalido"),
                @ApiResponse(responseCode = "401", description = "Não autorizado"),
                @ApiResponse(responseCode = "404", description = "Não encontrado"),
                @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        })

        public @interface SwaggerCreateFornecedor {
        }

        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)

        @Operation(
                summary = "Lista fornecedor por id",
                description = "Retorna o fornecedor com o id selecionado"
        )
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "fornecedor encontrado com sucesso"),
                @ApiResponse(responseCode = "400", description = "Pedido invalido"),
                @ApiResponse(responseCode = "401", description = "Não autorizado"),
                @ApiResponse(responseCode = "404", description = "Não encontrado"),
                @ApiResponse(responseCode = "500", description = "Erro interno do servidor")})

        public @interface SwaggerFindIdFornecedor {
        }

        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @Operation(
                summary = "Lista todas os fornecedores",
                description = "Retorna uma lista paginada de todos os fornecedores cadastradas"
        )
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "fornecedores encontradas com sucesso"),
                @ApiResponse(responseCode = "400", description = "pedido invalido"),
                @ApiResponse(responseCode = "401", description = "Não autorizado"),
                @ApiResponse(responseCode = "404", description = "Não encontrado"),
                @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        })

        public @interface SwaggerGetAllFornecedores {
        }


        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @Operation(
                summary = "Atualiza o fornecedor",
                description = "Atualiza as informaçoes de um fornecedor"
        )
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Fornecedor atualizado com sucesso"),
                @ApiResponse(responseCode = "400", description = "pedido invalido"),
                @ApiResponse(responseCode = "401", description = "Não autorizado"),
                @ApiResponse(responseCode = "404", description = "Não encontrado"),
                @ApiResponse(responseCode = "500", description = "Erro interno do servidor")})

        public @interface SwaggerUpdateFornecedor {
        }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Lista os produtos do fornecedor",
            description = "Lista todos os produtos que o fornecedor tem"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "produtos listados com sucesso"),
            @ApiResponse(responseCode = "400", description = "pedido invalido"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")})

    public @interface SwaggerlistarProdutosPorFornecedor {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Deleta o produto indicado do fornecedor",
            description = "Deleta todos os produto indicado pelo ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "produto deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "pedido invalido"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")})




    public @interface SwaggerdeletarProdutoPorFornecedor {

    }
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Adicionar Produto",
            description = "Adiciona produto ao fornecedor"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "produto adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "pedido invalido"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")})





    public @interface SwaggeradicionarProdutoAoFornecedor{

    }




    }



