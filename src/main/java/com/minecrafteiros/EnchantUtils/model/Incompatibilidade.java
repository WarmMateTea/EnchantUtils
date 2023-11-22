package com.minecrafteiros.EnchantUtils.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Incompatibilidade {
    @Id
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "enchantment_id_a")
    private Encantamento_Referencia e1;
    @ManyToOne
    @JoinColumn(name = "enchantment_id_b")
    private Encantamento_Referencia e2;


}
