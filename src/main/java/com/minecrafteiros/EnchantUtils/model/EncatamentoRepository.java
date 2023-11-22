package com.minecrafteiros.EnchantUtils.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EncatamentoRepository extends JpaRepository <Encantamento,Long> {
    @Query(value = "SELECT * FROM encantamento WHERE tesouro = true", nativeQuery = true)
    List<Encantamento> findAllTesouros();
    // Derived Query
    List<Encantamento> findAllByTesouroTrue();
    List<Encantamento> findByNome(String nome);
}
