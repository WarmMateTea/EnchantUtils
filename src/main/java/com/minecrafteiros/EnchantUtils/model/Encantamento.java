package com.minecrafteiros.EnchantUtils.model;

import jakarta.persistence.*;

@Entity
@Table(name="encantamento")
public class Encantamento {

    public Encantamento(EncantamentoDataRecord data) {
        this.nome = data.nome();
        this.descricao = data.descricao();
        this.tesouro = data.tesouro();
        this.nivel_max = data.nivel_max();
        this.peso_encantamento = data.peso_encantamento();
    }

    public Encantamento(String nome, String descricao, Boolean tesouro, Integer nivel_max, Integer peso_encantamento) {
        this.nome = nome;
        this.descricao = descricao;
        this.tesouro = tesouro;
        this.nivel_max = nivel_max;
        this.peso_encantamento = peso_encantamento;
    }

    public Encantamento() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private Boolean tesouro;
    private Integer nivel_max;
    private Integer peso_encantamento;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getTesouro() {
        return tesouro;
    }

    public void setTesouro(Boolean tesouro) {
        this.tesouro = tesouro;
    }

    public Integer getNivel_max() {
        return nivel_max;
    }

    public void setNivel_max(Integer nivel_max) {
        this.nivel_max = nivel_max;
    }

    public Integer getPeso_encantamento() {
        return peso_encantamento;
    }

    public void setPeso_encantamento(Integer peso_encantamento) {
        this.peso_encantamento = peso_encantamento;
    }

    // Relacionamento com outras tabelas
    // incompatibilidade
    // itensPrimarios
    // itensSecundarios
}
