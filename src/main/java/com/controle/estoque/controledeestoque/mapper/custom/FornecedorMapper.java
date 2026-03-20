package com.controle.estoque.controledeestoque.mapper.custom;

import com.controle.estoque.controledeestoque.DTO.RequestFornecedorDTo;
import com.controle.estoque.controledeestoque.DTO.ResponseFornecedorDTo;
import com.controle.estoque.controledeestoque.model.Fornecedor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface FornecedorMapper {
    Fornecedor toEntity(RequestFornecedorDTo dto);
    ResponseFornecedorDTo toDTO(Fornecedor fornecedor);

    List<Fornecedor> toEntity(List<RequestFornecedorDTo> dtoList);
    List<ResponseFornecedorDTo> toDTO(List<Fornecedor> fornecedorList);


}
