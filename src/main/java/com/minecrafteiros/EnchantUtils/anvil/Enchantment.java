package com.minecrafteiros.EnchantUtils.anvil;

import java.util.ArrayList;
import java.util.List;

public class Enchantment {

    @Override
    public String toString() {
        return "EnchId:"+ Id +"|EnchName:"+ Name +"|Lvl:" + Level + "|MaxLvl: " + Max_Level;
    }

    //como funciona o value: ele faz parte de um item, e é calculado a partir do encantamento. tem uma tabela que dá o valor de cada encantamento para cada item.

    public Enchantment(EnchantmentReferences.EnchantmentReference ER, String name, Integer level) {
        Id = ER.getIdentifier();
        Name = name;
        Level = level;
        Max_Level = ER.getMax_level();
        Item_Multiplier = ER.getItem_multiplier();
        Book_Multiplier = ER.getBook_multiplier();
    }

    public Enchantment(String id, String name, Integer level, Integer maxLevel, Integer item_Multiplier, Integer book_Multiplier) {
        Id = id;
        Name = name;
        Level = level;
        Max_Level = maxLevel;
        Item_Multiplier = item_Multiplier;
        Book_Multiplier = book_Multiplier;
    }

    public Integer getMultiplier(Item Sacrifice) {
        return (Sacrifice.getItemType().equalsIgnoreCase("BOOK")) ? getBook_Multiplier() : getItem_Multiplier();
    }

    public Enchantment(Enchantment another) {
        Id = another.getId();
        Name = another.getName();
        Level = another.getLevel();
        Max_Level = another.getMax_Level();
        Item_Multiplier = another.getItem_Multiplier();
        Book_Multiplier = another.getBook_Multiplier();
    }

    public ArrayList<Enchantment> getIncompatible_enchantments() {
        List<String> listaIdentifIncompativeis =  EnchantmentReferences.EnchantRef.get(Id).getIncompatibilities();  //query na database; select incompatible_enchantments table
        ArrayList<Enchantment> returnValue = new ArrayList<>();
        for (String id : listaIdentifIncompativeis)
            returnValue.add(new Enchantment(
                    id,
                    id,
                    0,
                    EnchantmentReferences.EnchantRef.get(Id).getMax_level(),
                    EnchantmentReferences.EnchantRef.get(Id).getItem_multiplier(),
                    EnchantmentReferences.EnchantRef.get(Id).getBook_multiplier()
            ));
        return returnValue;
    }

    public Boolean canBeAppliedTo(Item Target) {
        ArrayList<String> items = new ArrayList<>();
        for (String itemName : EnchantmentReferences.EnchantRef.get(Id).getPrimaryItems())
            items.add(itemName);
        for (String itemName : EnchantmentReferences.EnchantRef.get(Id).getSecondaryItems())
            items.add(itemName);
        items.add("book");

        return items.contains(Target.getItemType().toLowerCase());
        //return (Target.getItemType() is in this.primary_items or this.secondary_items);
        //query na database; is TargetItemType in enchantment.primary_items or enchantment.secondary_items
    }

    public String getEnchantmentName() {
        return EnchantmentReferences.EnchantRef.get(Id).identifier;   //checar na DB
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getLevel() {
        return Level;
    }

    public void setLevel(Integer level) {
        Level = (level > Max_Level) ? Max_Level : level;
    }

    public Integer getMax_Level() {
        return Max_Level;
    }

    public void setMax_Level(Integer max_Level) {
        Max_Level = max_Level;
    }

    private Integer getItem_Multiplier() {
        return Item_Multiplier;
    }

    private void setItem_Multiplier(Integer item_Multiplier) {
        Item_Multiplier = item_Multiplier;
    }

    private Integer getBook_Multiplier() {
        return Book_Multiplier;
    }

    private void setBook_Multiplier(Integer book_Multiplier) {
        Book_Multiplier = book_Multiplier;
    }

    private String Id;  //id dessa instancia de encantamento na db
    private String Name;
    private Integer Level;
    private Integer Max_Level;
    private Integer Item_Multiplier;
    private Integer Book_Multiplier;
}
