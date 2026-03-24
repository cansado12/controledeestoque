package com.controle.estoque.controledeestoque.repository;

import com.controle.estoque.controledeestoque.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    boolean existsByCnpj(String cnpj);



}
