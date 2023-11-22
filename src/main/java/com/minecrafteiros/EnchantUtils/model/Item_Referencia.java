package com.minecrafteiros.EnchantUtils.model;

import jakarta.persistence.*;

import java.awt.print.Book;
import java.util.Set;

@Entity
public class Item_Referencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    String string_id;
    String display_name;
    item_types item_type;

    public static enum item_types {
        BOOK, SWORD, AXE, PICKAXE, SHOVEL, HOE, COMPASS,
        HELMET,  CHESTPLATE,  LEGGINGS, BOOTS, SHIELD
    }

    @OneToMany(mappedBy = "instance_of")
    private Set<Item> type_instances;

    @ManyToMany(mappedBy = "primary_items")
    private Set<Encantamento_Referencia> is_primary_item_of;

    @ManyToMany(mappedBy = "secondary_items")
    private Set<Encantamento_Referencia> is_secondary_item_of;
}
