package com.minecrafteiros.EnchantUtils.model;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Encantamento_Referencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    String string_id;
    String display_name;
    String description;
    Boolean is_treasure;
    Integer max_level;
    Integer item_multiplier;
    Integer book_multiplier;
    Integer enchantment_weight;

    @OneToMany(mappedBy = "instance_of")
    private Set<Encantamento> type_instances;

    @ManyToMany
    @JoinTable(name = "primary_items", joinColumns = @JoinColumn(name = "id_encantamento"), inverseJoinColumns = @JoinColumn(name = "id_primary_item"))
    Set<Item_Referencia> primary_items;

    @ManyToMany
    @JoinTable(name = "secondary_items", joinColumns = @JoinColumn(name = "id_encantamento"), inverseJoinColumns = @JoinColumn(name = "id_secondary_item"))
    Set<Item_Referencia> secondary_items;

    public Encantamento_Referencia(String string_id, String display_name, String description, Boolean is_treasure, Integer max_level, Integer item_multiplier, Integer book_multiplier, Integer enchantment_weight, Set<Item_Referencia> primary_items, Set<Item_Referencia> secondary_items) {
        this.string_id = string_id;
        this.display_name = display_name;
        this.description = description;
        this.is_treasure = is_treasure;
        this.max_level = max_level;
        this.item_multiplier = item_multiplier;
        this.book_multiplier = book_multiplier;
        this.enchantment_weight = enchantment_weight;
        this.primary_items = primary_items;
        this.secondary_items = secondary_items;
    }

    public Encantamento_Referencia(Encantamento_ReferenciaDataRecord dr) {
        this.string_id = dr.string_id();
        this.display_name = dr.display_name();
        this.description = dr.description();
        this.is_treasure = dr.is_treasure();
        this.max_level = dr.max_level();
        this.item_multiplier = dr.item_multiplier();
        this.book_multiplier = dr.book_multiplier();
        this.enchantment_weight = dr.enchantment_weight();
        this.primary_items = dr.primary_items();
        this.secondary_items = dr.secondary_items();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getString_id() {
        return string_id;
    }

    public void setString_id(String string_id) {
        this.string_id = string_id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIs_treasure() {
        return is_treasure;
    }

    public void setIs_treasure(Boolean is_treasure) {
        this.is_treasure = is_treasure;
    }

    public Integer getMax_level() {
        return max_level;
    }

    public void setMax_level(Integer max_level) {
        this.max_level = max_level;
    }

    public Integer getItem_multiplier() {
        return item_multiplier;
    }

    public void setItem_multiplier(Integer item_multiplier) {
        this.item_multiplier = item_multiplier;
    }

    public Integer getBook_multiplier() {
        return book_multiplier;
    }

    public void setBook_multiplier(Integer book_multiplier) {
        this.book_multiplier = book_multiplier;
    }

    public Integer getEnchantment_weight() {
        return enchantment_weight;
    }

    public void setEnchantment_weight(Integer enchantment_weight) {
        this.enchantment_weight = enchantment_weight;
    }

    public Set<Encantamento> getType_instances() {
        return type_instances;
    }

    public void setType_instances(Set<Encantamento> type_instances) {
        this.type_instances = type_instances;
    }

    public Set<Item_Referencia> getPrimary_items() {
        return primary_items;
    }

    public void setPrimary_items(Set<Item_Referencia> primary_items) {
        this.primary_items = primary_items;
    }

    public Set<Item_Referencia> getSecondary_items() {
        return secondary_items;
    }

    public void setSecondary_items(Set<Item_Referencia> secondary_items) {
        this.secondary_items = secondary_items;
    }
}
