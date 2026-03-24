package com.controle.estoque.controledeestoque.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name = "fornecedor")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 155)
    @Schema(description = "Nome do fornecedor")
    @NotBlank
    private String nome;

    @Column(name = "cnpj", nullable = false)
    @Schema(description = "cnpj do fornecedor")
    @NotBlank
    private String cnpj;

    @Column(name = "email", nullable = false, length = 155)
    @Schema(description = "Email do fornecedor")
    @NotBlank
    private String email;
    @Column(name = "telefone", nullable = false)
    @Schema(description = "Telefone do fornecedor")
    @NotBlank
    private String telefone;

    @Column(name = "ativo", nullable = false, columnDefinition = "boolean default true")
    @Schema(description = "fornecedor ativo ou nao")
    @NotNull
    private boolean ativo;


    @ManyToMany(mappedBy = "fornecedores")
    private List<Produto> produtos =  new ArrayList<>();


    public Fornecedor() {
    }


    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Fornecedor that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
