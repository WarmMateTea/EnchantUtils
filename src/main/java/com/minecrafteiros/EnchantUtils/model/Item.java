package com.minecrafteiros.EnchantUtils.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    String custom_name;
    Integer repair_cost;
    Integer anvil_use_count;

    @ManyToOne
    @JoinColumn(name = "instance_id", nullable = false)
    private Item_Referencia instance_of;

    @OneToMany(mappedBy = "applied_to_item")
    private Set<Encantamento> enchantments;

    @OneToMany(mappedBy = "stored_in_item")
    private Set<Encantamento> stored_enchantments;

    public Item() {}
}
