package com.minecrafteiros.EnchantUtils.model;

public class EncantamentoDTO {
    private String nome;
    private Long id;
    private Integer nivel;

    public EncantamentoDTO() {
        // Construtor padrão necessário para a desserialização JSON
    }

    public EncantamentoDTO(String nome, Long id, Integer nivel) {
        this.nome = nome;
        this.id = id;
        this.nivel = nivel;
    }

    // Getters e setters para todas as propriedades

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }
}
