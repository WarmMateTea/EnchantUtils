package com.minecrafteiros.EnchantUtils.anvil;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Anvil anvil = new Anvil();

        ArrayList<Enchantment> de = new ArrayList<>();
        de.add(new Enchantment(EnchantmentReferences.EnchantRef.get("feather_falling"), "", 1));
        de.add(new Enchantment(EnchantmentReferences.EnchantRef.get("depth_strider"), "", 1));
        de.add(new Enchantment(EnchantmentReferences.EnchantRef.get("mending"), "", 1));

        var AR = anvil.Request(new Item("Diamond Boots", "BOOTS"), de);

        System.out.println(AR.getResult().toString());
    }
}