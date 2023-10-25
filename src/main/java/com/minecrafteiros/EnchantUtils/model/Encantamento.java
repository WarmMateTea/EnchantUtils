package com.minecrafteiros.EnchantUtils.model;

import jakarta.persistence.*;

@Entity
@Table(name="encantamento")
public class Encantamento {

    public Encantamento(EncantamentoDataRecord data) {
        this.nome = data.nome();
        this.descricao = data.descricao();
        this.tesouro = data.tesouro();
        this.nivelMax = data.nivelMax();
        this.pesoEncantamento = data.pesoEncantamento();
    }

    public Encantamento(String nome, String descricao, Boolean tesouro, Integer nivelMax, Integer pesoEncantamento) {
        this.nome = nome;
        this.descricao = descricao;
        this.tesouro = tesouro;
        this.nivelMax = nivelMax;
        this.pesoEncantamento = pesoEncantamento;
    }

    public Encantamento() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private Boolean tesouro;
    @Column(name = "nivelMax")
    private Integer nivelMax;
    @Column(name = "pesoEncantamento")
    private Integer pesoEncantamento;

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

    public Integer getNivelMax() {
        return nivelMax;
    }

    public void setNivelMax(Integer nivelMax) {
        this.nivelMax = nivelMax;
    }

    public Integer getPesoEncantamento() {
        return pesoEncantamento;
    }

    public void setPesoEncantamento(Integer pesoEncantamento) {
        this.pesoEncantamento = pesoEncantamento;
    }

    // Relacionamento com outras tabelas
    // incompatibilidade
    // itensPrimarios
    // itensSecundarios
}
