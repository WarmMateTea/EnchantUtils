package com.minecrafteiros.EnchantUtils.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Encantamento_ReferenciaRepository extends JpaRepository <Encantamento_Referencia,Long> {
    Encantamento_Referencia findByStringId(String string_id);
}
