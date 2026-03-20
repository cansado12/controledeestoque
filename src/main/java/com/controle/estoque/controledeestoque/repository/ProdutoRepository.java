package com.controle.estoque.controledeestoque.repository;

import com.controle.estoque.controledeestoque.model.Categoria;
import com.controle.estoque.controledeestoque.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Page<Produto> findByCategoria(Categoria categoria, Pageable pageable);




}
