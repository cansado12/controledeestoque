package com.controle.estoque.controledeestoque.controller;

import com.controle.estoque.controledeestoque.DTO.RequestFornecedorDTo;
import com.controle.estoque.controledeestoque.repository.FornecedorRepository;
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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;






@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FornecedorControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private ObjectMapper objectMapper;


    private RequestFornecedorDTo requestValido;

    @BeforeEach
    void setUp() {
        fornecedorRepository.deleteAll();

        requestValido = new RequestFornecedorDTo(
                "Fornecedor Teste",
                "12312312312345",      // 14 dígitos
                "teste@empresa.com",
                "11999999999",
                true,
                List.of()
        );
    }

    @Test
    @DisplayName("Post / fornecedores: Deve criar um fornecedor  e retornar 201")
    void criarFornecedorE_Retornar201() throws Exception {

        mockMvc.perform(post("/fornecedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Fornecedor Teste"))
                .andExpect(jsonPath("$.cnpj").value("12312312312345"))
                .andExpect(jsonPath("$.ativo").value(true))
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Test

    @DisplayName("POST /fornecedores - CNPJ duplicado deve retornar 400")
    void criarFornecedor_cnpjDuplicado_deveRetornar400() throws Exception {
        mockMvc.perform(post("/fornecedores")
        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/fornecedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isConflict());




    }

    @Test
    @DisplayName("POST /fornecedores - body inválido (nome em branco) deve retornar 400")
    void criarFornecedor_bodyInvalido_deveRetornar400() throws Exception {

        RequestFornecedorDTo requestInvalido= new RequestFornecedorDTo("","12312312312345",      // 14 dígitos
                "teste@empresa.com",
                "11999999999",
                true,
                List.of());

        mockMvc.perform(post("/fornecedores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestInvalido)))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("GET /fornecedores - deve retornar lista paginada com 200")
    void listarFornecedores_deveRetornar201() throws Exception {
        String resposta = mockMvc.perform(post("/fornecedores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(resposta).get("id").asLong();

        mockMvc.perform(get("/fornecedores/{id}", id)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id").value(id)).andExpect(jsonPath("$.nome").
                        value("Fornecedor Teste"));

    }


    @Test
    @DisplayName("GET /fornecedores/{id} - ID inexistente deve retornar 404")
    void buscarPorId_inexistente_deveRetornar404() throws Exception {

       mockMvc.perform(get("/fornecedores/{id}", 999L))
               .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /fornecedores/{id} - deve atualizar e retornar 200")
    void atualizar_deveRetornar200() throws Exception {
        String resposta = mockMvc.perform(post("/fornecedores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(resposta).get("id").asLong();

        RequestFornecedorDTo requestAtualizado = new RequestFornecedorDTo("david",
                "12332112332123",
                "Louco@gmail",
                "22999039944", true, List.of());

        mockMvc.perform(put("/fornecedores/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("david"))
                .andExpect(jsonPath("$.email").value("Louco@gmail"));



    }

    @Test
    @DisplayName("DELETE /fornecedores/{id} - deve deletar e retornar 204")
    void deletar_deveRetornar204() throws Exception {
        String resposta =  mockMvc.perform(post("/fornecedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(resposta).get("id").asLong();

        mockMvc.perform(delete("/fornecedores/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isNoContent());


    }

    @Test
    @DisplayName("PATCH /fornecedores/{id}/desativar - deve desativar e retornar 204")
    void desativar_deveRetornar204() throws Exception {
        String resposta =  mockMvc.perform(post("/fornecedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(resposta).get("id").asLong();

        mockMvc.perform(patch("/fornecedores/{fornecedorId}/desativar", id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/fornecedores/{id}", id))
                .andExpect(status().isOk()).andExpect(jsonPath("$.ativo").value(false));








    }

}