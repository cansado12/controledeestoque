package com.controle.estoque.controledeestoque.mapper.custom;

import com.controle.estoque.controledeestoque.DTO.RequestFornecedorDTo;
import com.controle.estoque.controledeestoque.DTO.ResponseFornecedorDTO;
import com.controle.estoque.controledeestoque.model.Fornecedor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")

public interface FornecedorMapper {
    @Mapping(source = "status", target = "ativo")
    Fornecedor toEntity(RequestFornecedorDTo dto);
    ResponseFornecedorDTO toDTO(Fornecedor fornecedor);

    List<Fornecedor> toEntity(List<RequestFornecedorDTo> dtoList);
    List<ResponseFornecedorDTO> toDTO(List<Fornecedor> fornecedorList);


}
