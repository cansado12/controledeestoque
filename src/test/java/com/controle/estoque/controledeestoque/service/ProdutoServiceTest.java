package com.controle.estoque.controledeestoque.service;

import com.controle.estoque.controledeestoque.DTO.RequestDTO;
import com.controle.estoque.controledeestoque.DTO.ResponseDTO;
import com.controle.estoque.controledeestoque.exception.ProdutoNotFoundException;
import com.controle.estoque.controledeestoque.mapper.custom.ProdutoMapper;
import com.controle.estoque.controledeestoque.model.Categoria;
import com.controle.estoque.controledeestoque.model.Produto;
import com.controle.estoque.controledeestoque.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository repository;

    @InjectMocks
    private ProdutoService service;

    @Mock
    private ProdutoMapper mapper;

    private Produto produto;
    private ResponseDTO responseDTO;
    private RequestDTO requestDTO;
    private Pageable pageable;



    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 10);

        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Fornecedor");
        produto.setPreco(new BigDecimal("2.25"));
        produto.setQuantidade(5);
        produto.setCategoria(Categoria.ALIMENTOS);
        produto.setFornecedores(new ArrayList<>());

        responseDTO = new ResponseDTO(1L, "bala", new BigDecimal("2.25"), 5, Categoria.ALIMENTOS);
        requestDTO = new RequestDTO("bala", new BigDecimal("2.25"), 5, Categoria.ALIMENTOS, new ArrayList<>());
    }

    @Test
    @DisplayName("findAll - deve retornar página de DTOs")
    void findAll() {
        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(produto)));
        when(mapper.toDTO(produto)).thenReturn(responseDTO);

        Page<ResponseDTO> result = service.findAll(pageable);

        assertThat(result).hasSize(1);
        verify(repository).findAll(pageable);
    }

    @Test
    @DisplayName("findById - deve retornar DTO quando ID existe")
    void findById() {
        when(repository.findById(1L)).thenReturn(Optional.of(produto));
        when(mapper.toDTO(produto)).thenReturn(responseDTO);

        ResponseDTO result = service.findById(1L);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.nome()).isEqualTo("bala");
    }

    @Test
    @DisplayName("findById - deve lançar exception quando ID não existe")
    void findByIdException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99L))
                .isInstanceOf(ProdutoNotFoundException.class)
                .hasMessage("Produto com id 99 nao encontrado");
    }

    @Test
    @DisplayName("update - deve atualizar campos e retornar DTO")
    void update() {
        RequestDTO novosDados = new RequestDTO("Placa de video", new BigDecimal("3000"), 12, Categoria.INFORMATICA, new ArrayList<>());
        ResponseDTO dtoAtualizado = new ResponseDTO(2L, "Placa de video", new BigDecimal("3000"), 12, Categoria.INFORMATICA);

        when(repository.findById(1L)).thenReturn(Optional.of(produto));
        when(repository.save(produto)).thenReturn(produto);
        when(mapper.toDTO(produto)).thenReturn(dtoAtualizado);

        ResponseDTO update = service.update(1L, novosDados);

        assertThat(update.nome()).isEqualTo("Placa de video");
        assertThat(update.quantidade()).isEqualTo(12);
        verify(repository).save(produto);
    }

    @Test
    @DisplayName("update - deve lançar exception quando ID não existe")
    void updateIdException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(1L, requestDTO))
                .isInstanceOf(ProdutoNotFoundException.class);
    }

    @Test
    @DisplayName("delete - deve chamar deleteById quando ID existe")
    void delete() {
        when(repository.findById(1L)).thenReturn(Optional.of(produto));

        service.delete(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("delete - deve lançar exception quando ID não existe")
    void deleteIdException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(1L))
                .isInstanceOf(ProdutoNotFoundException.class);

        verify(repository, never()).deleteById(1L);
    }

    @Test
    @DisplayName("create - deve salvar e retornar DTO")
    void create() {
        when(mapper.toEntity(requestDTO)).thenReturn(produto);
        when(repository.save(produto)).thenReturn(produto);
        when(mapper.toDTO(produto)).thenReturn(responseDTO);

        ResponseDTO result = service.create(requestDTO);

        assertThat(result.nome()).isEqualTo("bala");
        verify(repository).save(produto);
    }

    @Test
    @DisplayName("create - deve setar categoria OUTROS quando categoria for nula")
    void createStatusPendente() {
        RequestDTO semCategoria = new RequestDTO("camisa", new BigDecimal("3000"), 12, null, new ArrayList<>());
        Produto produtoSemCategoria = new Produto();
        produtoSemCategoria.setCategoria(null);

        when(mapper.toEntity(semCategoria)).thenReturn(produtoSemCategoria);
        when(repository.save(any(Produto.class))).thenReturn(produtoSemCategoria);
        when(mapper.toDTO(produtoSemCategoria)).thenReturn(responseDTO);

        ResponseDTO result = service.create(semCategoria);

        assertThat(result.categoria()).isEqualTo(Categoria.ALIMENTOS);
    }

    @Test
    @DisplayName("findByCategoria - deve retornar produtos filtrados pela categoria")
    void findByCategoria() {
        when(repository.findByCategoria(Categoria.ALIMENTOS, pageable))
                .thenReturn(new PageImpl<>(List.of(produto)));
        when(mapper.toDTO(produto)).thenReturn(responseDTO);

        Page<ResponseDTO> byCategoria = service.findByCategoria(Categoria.ALIMENTOS, pageable);

        assertThat(byCategoria).hasSize(1);
        assertThat(byCategoria.getContent().get(0)).isEqualTo(responseDTO);
    }
}