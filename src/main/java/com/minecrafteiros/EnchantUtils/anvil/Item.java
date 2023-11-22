package com.minecrafteiros.EnchantUtils.anvil;

import java.util.ArrayList;
import java.util.List;

public class Item {

    @Override
    public String toString() {
        return "Item Name: " + Name() + "\nEnchantments (Applied): " + getEnchantments() + "\nEnchantments (Stored): " + getStoredEnchantments() + "\nAUC: " + getAnvilUseCount() + "\nItem Type: " + getItemType();
    }

    public Item(String name, String itemType) {
        this.name = name;
        this.itemType = itemType;
        enchantments = new ArrayList<Enchantment>();
        storedEnchantments = new ArrayList<Enchantment>();
        repairCost = 0;
        anvilUseCount = 0;
    }

    public Item(Item another) {
        this.name = another.name;
        this.itemType = another.itemType;

        enchantments = new ArrayList<Enchantment>();
        for (Enchantment E : another.getEnchantments()) {
            enchantments.add(new Enchantment(E));
        }

        storedEnchantments = new ArrayList<Enchantment>();
        for (Enchantment E : another.getStoredEnchantments()) {
            storedEnchantments.add(new Enchantment(E));
        }

        repairCost = another.repairCost;
        anvilUseCount = another.anvilUseCount;
    }

    private String name;
    private List<Enchantment> enchantments;
    private List<Enchantment> storedEnchantments;
    private Integer repairCost;
    private Integer anvilUseCount;
    private String itemType;

    public Boolean hasIncompatibleEnchantmentTo(Enchantment enchantment) {
        return this.getIncompatibleEnchantments().stream()
                .filter(incompatible -> incompatible.getId().equalsIgnoreCase(enchantment.getId()))
                .findAny()
                .orElse(null) != null; // Se encontrar um encantamento incompatível, retorna true; senão, false.
    }

    public Enchantment findEqualEnchantmentAs(Enchantment query_enchantment) {
        List<Enchantment> enchantList = (this.itemType.equalsIgnoreCase("BOOK")) ? this.storedEnchantments : this.enchantments;
        return enchantList.stream()
                .filter(e -> e.getId().equalsIgnoreCase(query_enchantment.getId()))
                .findFirst()
                .orElse(null);
    }

    public Boolean hasAnyIncompatibleEnchantmentTo(Enchantment query_enchantment) {
       return query_enchantment.getIncompatible_enchantments().stream()
               .filter(ie -> ie.getEnchantmentName().equalsIgnoreCase(query_enchantment.getEnchantmentName()))
               .findAny()
               .orElse(null) != null;
    }

    public List<Enchantment> getIncompatibleEnchantments() {
        List<Enchantment> incompatibleEnchList = new ArrayList<>();
        List<Enchantment> itemEnchantments = (this.getItemType().equalsIgnoreCase("book") ? this.getStoredEnchantments() : this.getEnchantments());

        // aqui eu espero que o nome do encantamento seja exatamento o identificador string dele nas referências (ex: "aqua_affinity")
        // daí a necessidade de mudar o Enchantment pra substituir Name por Identifier e adicionar um DisplayName, que pode ser fetched da base de referência.
        for (Enchantment ench : itemEnchantments) {
            for (String incompEnchId : EnchantmentReferences.EnchantRef.get(ench.getId()).getIncompatibilities()) {
                incompatibleEnchList.add(new Enchantment(incompEnchId, incompEnchId, 0, 0, 0, 0));
            }
        }

        return incompatibleEnchList; // normalmente vai ser uma query na database que só me retorna os Identifiers extensos dos encantamentos de referência.
    }

    public String Name() {
        return name;
    }

    public List<Enchantment> getEnchantments() {
        return (this.itemType.equalsIgnoreCase("BOOK")) ? this.storedEnchantments : this.enchantments;
    }

    public void setEnchantments(List<Enchantment> enchantments) {
        this.enchantments = enchantments;
    }

    public List<Enchantment> getStoredEnchantments() {
        return storedEnchantments;
    }

    public void setStoredEnchantments(List<Enchantment> storedEnchantments) {
        this.storedEnchantments = storedEnchantments;
    }

    public void addStoredEnchantment(Enchantment e) {
        storedEnchantments.add(e);
    }

    public Integer RepairCost() {
        return repairCost;
    }

    public void RepairCost(Integer value) {
        repairCost = value;
    }

    public Integer getAnvilUseCount() {
        return anvilUseCount;
    }

    public void setAnvilUseCount(Integer anvilUseCount) {
        this.anvilUseCount = anvilUseCount;
    }

    public Integer getValue() {
        //calcular valor do item: dependendo do tipo de item, o cálculo muda.
        List<Enchantment> elist = ItemEnchantmentListHandler(this);
        int value = 0;
        for (Enchantment e : elist) {
            value += e.getMultiplier(this) * e.getLevel();
        }
        return value;
    }

    public Integer getPriorWorkPenalty() {
        return 2^(anvilUseCount) - 1;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    private List<Enchantment> ItemEnchantmentListHandler(Item item) {
        if (item.getItemType().equalsIgnoreCase("BOOK"))
            return item.getStoredEnchantments();
        else
            return item.getEnchantments();
    }
}
