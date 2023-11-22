package com.minecrafteiros.EnchantUtils.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Item_ReferenciaRepository extends JpaRepository <Item_Referencia, Long> {
    Item_Referencia findByStringId(String string_id);
}
