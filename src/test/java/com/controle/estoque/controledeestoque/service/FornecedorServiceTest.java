package com.controle.estoque.controledeestoque.service;

import com.controle.estoque.controledeestoque.DTO.ProdutoDTo;
import com.controle.estoque.controledeestoque.DTO.RequestFornecedorDTo;
import com.controle.estoque.controledeestoque.DTO.ResponseFornecedorDTO;
import com.controle.estoque.controledeestoque.exception.FornecedorNotFoundException;
import com.controle.estoque.controledeestoque.exception.ProdutoNotFoundException;
import com.controle.estoque.controledeestoque.mapper.custom.FornecedorMapper;
import com.controle.estoque.controledeestoque.model.Categoria;
import com.controle.estoque.controledeestoque.model.Fornecedor;
import com.controle.estoque.controledeestoque.model.Produto;
import com.controle.estoque.controledeestoque.repository.FornecedorRepository;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class FornecedorServiceTest {

    @Mock
    FornecedorRepository repository;

    @Mock
    FornecedorMapper mapper;

    @InjectMocks
    FornecedorService service;

    @Mock
    ProdutoRepository produtoRepository;


    private Fornecedor fornecedor;
    private PageRequest pageable;
    private RequestFornecedorDTo  requestDTo;
    private ResponseFornecedorDTO responseDTo;
    private Produto produto;





    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 10);
        fornecedor = new Fornecedor();
        fornecedor.setId(1L);
        fornecedor.setNome("Fornecedor");
        fornecedor.setTelefone("22999069944");
        fornecedor.setEmail("Loucobr882@gmail.com");
        fornecedor.setCnpj("123123123123");
        fornecedor.setProdutos(new ArrayList<>());
        fornecedor.setAtivo(true);

        requestDTo = new RequestFornecedorDTo("Fornecedor","123123123123", "Loucobr882@gmail.com", "22999069944", true ,new ArrayList<>() );
        responseDTo = new ResponseFornecedorDTO(1L, "Fornecedor", "123123123123", "loucobr882@gmail.com", "22999069944", new ArrayList<>(), true);


        produto = new Produto();
        produto.setId(1L);
        produto.setNome("bala");
        produto.setPreco(new BigDecimal("2.25"));
        produto.setQuantidade(5);
        produto.setCategoria(Categoria.ALIMENTOS);
        produto.setFornecedores(new ArrayList<>());



    }

    @Test
    @DisplayName("Criar- deve criar fornecedor")
    void criar() {
        when(mapper.toEntity(requestDTo)).thenReturn(fornecedor);
        when(repository.save(fornecedor)).thenReturn(fornecedor);
        when(mapper.toDTO(fornecedor)).thenReturn(responseDTo);
        ResponseFornecedorDTO result = service.criar(requestDTo);


        assertThat(result.nome()).isEqualTo("Fornecedor");
        verify(repository).save(fornecedor);


    }




    @Test
    @DisplayName("findById - deve retornar DTO quando ID existe")
    void buscarPorId() {
       when(repository.findById(1L)).thenReturn(Optional.of(fornecedor));
       when(mapper.toDTO(fornecedor)).thenReturn(responseDTo);
       ResponseFornecedorDTO result = service.buscarPorId(1L);
       assertThat(result.id()).isEqualTo(1L);
       assertThat(result.nome()).isEqualTo("Fornecedor");


    }


    @Test
    @DisplayName("buscarPorIdNaoExiste - deve lançar exception quando ID não existe")
    void  buscarPorIdNaoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.buscarPorId(99L)).
                isInstanceOf(FornecedorNotFoundException.class);
    }



    @Test
    @DisplayName("listarTodos - deve listar todos os fornecedores")
    void listarTodos() {
        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(fornecedor)));
        when(mapper.toDTO(fornecedor)).thenReturn(responseDTo);

        Page <ResponseFornecedorDTO> result = service.listarTodos(pageable);
        assertThat(result.toList()).hasSize(1);

    }




    @Test
    @DisplayName("atualizar - deve atualizar dados do fornecedor")
    void atualizar() {
        RequestFornecedorDTo requestAtualizado = new RequestFornecedorDTo("luiz", "192931293","david@gmail", "2299921244", true ,new ArrayList<>());
        ResponseFornecedorDTO responseAtualizado = new ResponseFornecedorDTO(3L,"luiz", "192931293", "david@gmail", "2299921244", new ArrayList<>(), true);

        when(repository.findById(1L)).thenReturn(Optional.of(fornecedor));
        when(repository.save(fornecedor)).thenReturn(fornecedor);
        when(mapper.toDTO(fornecedor)).thenReturn(responseAtualizado);

        ResponseFornecedorDTO result = service.atualizar(1l, requestAtualizado);
        assertThat(result.nome()).isEqualTo("luiz");
        assertThat(result.id()).isEqualTo(3L);
        verify(repository).save(fornecedor);

    }



    @Test
    @DisplayName("atualizarNaoExiste - deve lançar exception quando ID não existe")
    void  atualizarNaoExiste() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.atualizar(1L, requestDTo)).
                isInstanceOf(FornecedorNotFoundException.class);
    }


    @Test
    @DisplayName("deletar -> deve deletar fornecedor pelo id")
    void deletar() {
        when(repository.findById(1L)).thenReturn(Optional.of(fornecedor));


       service.deletar(1L);
        verify(repository).deleteById(1l);


    }

    @Test
    @DisplayName("delete - deve lançar exception quando ID não existe")
    void deletarNaoExiste() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deletar(1L)).isInstanceOf(FornecedorNotFoundException.class);
        verify(repository, never()).deleteById(1L);
    }

    @Test
    @DisplayName("adicionarProduto - deve adicionar produto ao fornecedor")
    void adicionarProduto() {
        when(repository.findById(1L)).thenReturn(Optional.of(fornecedor));
        when(mapper.toDTO(fornecedor)).thenReturn(responseDTo);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        ResponseFornecedorDTO result = service.adicionarProduto(1L, 1L);
        assertThat(result.id()).isEqualTo(1L);
        verify(repository).save(fornecedor);



    }


    @Test
    @DisplayName("removerProduto - remove o produto do fornecedor")
    void removerProduto() {
        when(repository.findById(1L)).thenReturn(Optional.of(fornecedor));
        when(mapper.toDTO(fornecedor)).thenReturn(responseDTo);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        ResponseFornecedorDTO result = service.removerProduto(1L, 1L);
        assertThat(result.id()).isEqualTo(1L);
        verify(repository).save(fornecedor);



    }

    @Test
    @DisplayName("listarProdutosPorFornecedor - Lista todos os produtos por fornecedor")
    void listarProdutosPorFornecedor() {
        ProdutoDTo produtoDto = new ProdutoDTo(1L, "david", new BigDecimal(24.21),6, Categoria.ALIMENTOS );
        ResponseFornecedorDTO responseComProdutos = new ResponseFornecedorDTO(
                1L, "Fornecedor", "123123123123", "loucobr882@gmail.com", "22999069944",
                new ArrayList<>(List.of(produtoDto)), true
        );

        fornecedor.setProdutos(new ArrayList<>(List.of(produto)));
        when(repository.findById(1L)).thenReturn(Optional.of(fornecedor));
        when(mapper.toDTO(fornecedor)).thenReturn(responseComProdutos);

        ResponseFornecedorDTO result = service.listarProdutosPorFornecedor(1L);

        assertThat(result.produtos()).hasSize(1);
        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("produtoNaoExisteParaListar -> deve lançar excessao se produto nao existir")
    void produtoNaoExisteParaListar() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.listarProdutosPorFornecedor(1L)).isInstanceOf(FornecedorNotFoundException.class);
    }

    @Test
    @DisplayName("produtoNaoExisteParaRemover -> deve lançar excessao se produto nao existir")
    void produtoNaoExisteParaRemover() {
        when(repository.findById(1L)).thenReturn(Optional.of(fornecedor));
        when(produtoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.removerProduto(99L, 1L)).isInstanceOf(ProdutoNotFoundException.class);
    }

    @Test
    @DisplayName("adicionarProduto - deve lançar exceção se produto não existir")
    void adicionarProdutoNaoExiste() {
        when(repository.findById(1L)).thenReturn(Optional.of(fornecedor));
        when(produtoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.adicionarProduto(99L, 1L))
                .isInstanceOf(ProdutoNotFoundException.class);
    }

}