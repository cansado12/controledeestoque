package com.controle.estoque.controledeestoque.mapper.custom;

import com.controle.estoque.controledeestoque.DTO.ProdutoDTo;
import com.controle.estoque.controledeestoque.DTO.RequestDTO;
import com.controle.estoque.controledeestoque.DTO.ResponseDTO;
import com.controle.estoque.controledeestoque.model.Produto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    Produto toEntity(RequestDTO dto);
    ResponseDTO toDTO(Produto produto);
    ProdutoDTo toProdutoDTO(Produto produto);

    List<Produto> toEntity(List<RequestDTO> dtoList);
    List<ResponseDTO> toDTO(List<Produto> produtoList);

}
