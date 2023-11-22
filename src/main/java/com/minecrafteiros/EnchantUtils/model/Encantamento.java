package com.minecrafteiros.EnchantUtils.model;

import jakarta.persistence.*;

//Classe invisível ao usuário. Aquilo que ele vê são os encantamento_referencia
@Entity
@Table(name="encantamento")
public class Encantamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    Integer level;

    @ManyToOne
    @JoinColumn(name = "instance_id", nullable = false)
    private Encantamento_Referencia instance_of;

    @ManyToOne
    @JoinColumn(name = "enchanted_item_id", nullable = false)
    private Item applied_to_item;

    @ManyToOne
    @JoinColumn(name = "storage_item_id", nullable = false)
    private Item stored_in_item;

    public Encantamento() {}

    public Encantamento(Integer level, Encantamento_Referencia instance_of, Item applied_to_item, Item stored_in_item) {
        this.level = level;
        this.instance_of = instance_of;
        this.applied_to_item = applied_to_item;
        this.stored_in_item = stored_in_item;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Encantamento_Referencia getInstance_of() {
        return instance_of;
    }

    public void setInstance_of(Encantamento_Referencia instance_of) {
        this.instance_of = instance_of;
    }

    public Item getApplied_to_item() {
        return applied_to_item;
    }

    public void setApplied_to_item(Item applied_to_item) {
        this.applied_to_item = applied_to_item;
    }

    public Item getStored_in_item() {
        return stored_in_item;
    }

    public void setStored_in_item(Item stored_in_item) {
        this.stored_in_item = stored_in_item;
    }
}
