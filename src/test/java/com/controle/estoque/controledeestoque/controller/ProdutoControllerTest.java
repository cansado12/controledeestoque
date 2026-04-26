package com.controle.estoque.controledeestoque.controller;

import com.controle.estoque.controledeestoque.DTO.RequestDTO;
import com.controle.estoque.controledeestoque.model.Categoria;
import com.controle.estoque.controledeestoque.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    RequestDTO requestValido;



    @BeforeEach
    void setUp() {
        produtoRepository.deleteAll();

        requestValido = new RequestDTO("bala", new BigDecimal("22.11"), 10, Categoria.ALIMENTOS, List.of());

    }


    @Test
    @DisplayName("POST /produtos - deve criar produto e retornar 201")
    void criarProduto_Retornar201() throws Exception {
        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("bala"))
                .andExpect(jsonPath("$.quantidade").value(10))
                .andExpect(jsonPath("$.preco").value(22.11));

    }

    @Test
    @DisplayName("POST /produtos - body inválido deve retornar 400")
    void criarProduto_bodyInvalido_deveRetornar400() throws Exception {
        RequestDTO requestInvalido = new RequestDTO("", new BigDecimal("22.11"), 10, Categoria.ALIMENTOS, List.of());




        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestInvalido)))
                .andExpect(status().isBadRequest());
    }



    @Test
    @DisplayName("GET /produtos - deve retornar lista paginada com 200")
    void listarProdutos_deveRetornar200() throws Exception {
        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/produtos")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].nome").value("bala"));

    }

    @Test
    @DisplayName("GET /produtos/{id} - ID existente deve retornar 200")
    void buscarPorId_existente_deveRetornar200() throws Exception {
        String resposta = mockMvc.perform(post("/produtos")
        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(resposta).get("id").asLong();


        mockMvc.perform(get("/produtos/{id}", id).
                contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("bala"))
                .andExpect(jsonPath("$.quantidade").value(10));





    }


    @Test
    @DisplayName("PUT /produtos/{id} - deve atualizar e retornar 200")
    void atualizar_deveRetornar200() throws Exception {

      String resposta =   mockMvc.perform(post("/produtos")
        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

      Long id = objectMapper.readTree(resposta).get("id").asLong();


      RequestDTO requestNovo = new RequestDTO("faca", new BigDecimal("12.11"), 42, Categoria.INFORMATICA, List.of());


      mockMvc.perform(put("/produtos/{id}", id)
      .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(requestNovo)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.nome").value("faca"))
              .andExpect(jsonPath("$.quantidade").value(42));


    }


    @Test
    @DisplayName("PUT /produtos/{id} - ID inexistente deve retornar 404")
    void atualizar_inexistente_deveRetornar404() throws Exception {
        mockMvc.perform(put("/produtos/{id}", 333L)
        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isNotFound());





    }

    @Test
    @DisplayName("DELETE /produtos/{id} - deve deletar e retornar 204")
    void deletar_deveRetornar204() throws Exception {
        String resposta = mockMvc.perform(post("/produtos")
        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();


        Long id = objectMapper.readTree(resposta).get("id").asLong();


        mockMvc.perform(delete("/produtos/{id}", id))
                .andExpect(status().isNoContent());




        mockMvc.perform(get("/produtos/{id}", id))
                .andExpect(status().isNotFound());




    }


    @Test
    @DisplayName("DELETE /produtos/{id} - ID inexistente deve retornar 404")
    void deletar_inexistente_deveRetornar404() throws Exception {
        mockMvc.perform(delete("/produtos/{id}", 333L))
                .andExpect(status().isNotFound());




    }


    @Test
    @DisplayName("GET /produtos/categoria/{categoria} - deve retornar produtos da categoria")
    void buscarPorCategoria_deveRetornar200() throws Exception {
        mockMvc.perform(post("/produtos")
        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated());



      RequestDTO requestDTOCategoria = new RequestDTO("camisa", new BigDecimal("12.11"), 10, Categoria.VESTUARIO, List.of());


        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTOCategoria)))
                .andExpect(status().isCreated());


        mockMvc.perform(get("/produtos/categoria/{categoria}", "ALIMENTOS")
                .param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(hasSize(1)))
                .andExpect(jsonPath("$.content[0].categoria").value("ALIMENTOS"));


    }



    @Test
    @DisplayName("GET /produtos/categoria/{categoria} - categoria sem produtos retorna lista vazia")
    void buscarPorCategoria_semProdutos_deveRetornarListaVazia() throws Exception {
        mockMvc.perform(get("/produtos/categoria/{categoria}", "ELETRONICOS")
                .param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(hasSize(0)));




    }

    @Test
    @DisplayName("GET /produtos/exportPage - deve exportar PDF com 200")
    void exportarPaginaPdf_deveRetornar200() throws Exception {
        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/produtos/exportPage")
                        .param("page", "0")
                        .param("size", "10")
                        .header("Accept", MediaType.APPLICATION_PDF_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF));
    }







    }




