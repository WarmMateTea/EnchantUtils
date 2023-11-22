package com.minecrafteiros.EnchantUtils.anvil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnchantmentReferences {

    // Dá pra melhorar isso aqui colocando display names também. Mas isso é trabalho pra depois que a lógica estiver confirmada.
    public static class EnchantmentReference {
        public EnchantmentReference(String identifier, Integer max_level, Integer item_multiplier, Integer book_multiplier, Integer enchantment_weight, List<String> primaryItems, List<String> secondaryItems, List<String> incompatibilities) {
            this.identifier = identifier;
            this.max_level = max_level;
            this.item_multiplier = item_multiplier;
            this.book_multiplier = book_multiplier;
            this.enchantment_weight = enchantment_weight;
            PrimaryItems = primaryItems;
            SecondaryItems = secondaryItems;
            Incompatibilities = incompatibilities;
        }

        final String identifier;
        final Integer max_level;
        final Integer item_multiplier;
        final Integer book_multiplier;
        final Integer enchantment_weight;
        private final List<String> PrimaryItems;
        private final List<String> SecondaryItems;
        private final List<String> Incompatibilities;

        public String getIdentifier() {
            return identifier;
        }

        public Integer getMax_level() {
            return max_level;
        }

        public Integer getItem_multiplier() {
            return item_multiplier;
        }

        public Integer getBook_multiplier() {
            return book_multiplier;
        }

        public Integer getEnchantment_weight() {
            return enchantment_weight;
        }

        public List<String> getPrimaryItems() {
            return (PrimaryItems.get(0).equalsIgnoreCase("")) ? new ArrayList<>() : PrimaryItems;
        }

        public List<String> getSecondaryItems() {
            return (SecondaryItems.get(0).equalsIgnoreCase("")) ? new ArrayList<>() : SecondaryItems;
        }

        public List<String> getIncompatibilities() {
            return (Incompatibilities.get(0).equalsIgnoreCase("")) ? new ArrayList<>() : Incompatibilities;
        }
    }

    // aqui entra identificador (string) de encantamento, sai o que vc precisar (itens compatíveis, encantamentos incompatíveis, etc)

    //<editor-fold desc="Povoamento do EnchantRef com todos os encantamentos ¬¬' ">
    public static final Map<String, EnchantmentReference> EnchantRef = Map.ofEntries(
            Map.entry("aqua_affinity", new EnchantmentReference(
                    "aqua_affinity",
                    1,
                    4,
                    2,
                    0,
                    List.of("helmet"),
                    List.of(""),
                    List.of("")
                )),
            Map.entry("bane_of_arthropods", new EnchantmentReference(
                    "bane_of_arthropods",
                    5,
                    2,
                    1,
                    5,
                    List.of("sword"),
                    List.of("axe"),
                    List.of("smite","sharpness")
            )),
            Map.entry("blast_protection", new EnchantmentReference(
                    "blast_protection",
                    4,
                    4,
                    2,
                    2,
                    List.of("helmet","chestplate","leggings","boots"),
                    List.of(""),
                    List.of("fire_protection","protection","projectile_protection")
            )),
            Map.entry("channeling", new EnchantmentReference(
                    "channeling",
                    1,
                    8,
                    4,
                    1,
                    List.of("trident"),
                    List.of(""),
                    List.of("riptide"))
            ),
            Map.entry("curse_of_binding", new EnchantmentReference(
                    "curse_of_binding",
                    1,
                    8,
                    4,
                    1,
                    List.of(""),
                    List.of("helmet","chestplate","leggings","boots","pumpkin","elytra"),
                    List.of(""))
            ),
            Map.entry("curse_of_vanishing", new EnchantmentReference(
                    "curse_of_vanishing",
                    1,
                    8,
                    4,
                    1,
                    List.of(""),
                    List.of("pickaxe", "shovel", "axe", "fishing_rod", "helmet", "chestplate", "leggings", "boots", "sword", "bow", "hoe", "shears", "flint_and_steel", "carrot_on_a_stick", "fungus_on_a_stick", "shield", "elytra", "pumpkin", "trident", "crossbow", "compass", "death_compass"),
                    List.of(""))
            ),
            Map.entry("depth_strider", new EnchantmentReference(
                    "depth_strider",
                    3,
                    4,
                    2,
                    2,
                    List.of("boots"),
                    List.of(""),
                    List.of("frost_walker"))
            ),
            Map.entry("efficiency", new EnchantmentReference(
                    "efficiency",
                    5,
                    1,
                    1,
                    10,
                    List.of("pickaxe","shovel","axe","hoe"),
                    List.of("shears"),
                    List.of(""))
            ),
            Map.entry("feather_falling", new EnchantmentReference(
                    "feather_falling",
                    4,
                    2,
                    1,
                    5,
                    List.of("boots"),
                    List.of(""),
                    List.of(""))
            ),
            Map.entry("fire_aspect", new EnchantmentReference(
                    "fire_aspect",
                    2,
                    4,
                    2,
                    2,
                    List.of("sword"),
                    List.of(""),
                    List.of(""))
            ),
            Map.entry("fire_protection", new EnchantmentReference(
                    "fire_protection",
                    4, 2,
                    1,
                    5,
                    List.of("helmet","chestplate","leggings","boots"),
                    List.of(""),
                    List.of("blast_protection","protection","projectile_protection"))
            ),
            Map.entry("flame", new EnchantmentReference(
                    "flame",
                    1,
                    4,
                    2,
                    5,
                    List.of("bow"),
                    List.of(""),
                    List.of(""))
            ),
            Map.entry("fortune", new EnchantmentReference(
                    "fortune",
                    3,
                    4,
                    2,
                    2,
                    List.of("pickaxe","shovel","axe","hoe"),
                    List.of(""),
                    List.of("silk_touch"))
            ),
            Map.entry("frost_walker", new EnchantmentReference(
                    "frost_walker",
                    2,
                    4,
                    2,
                    2,
                    List.of("boots"),
                    List.of(""),
                    List.of("depth_strider"))
            ),
            Map.entry("impaling", new EnchantmentReference(
                    "impaling",
                    5,
                    4,
                    2,
                    2,
                    List.of("trident"),
                    List.of(""),
                    List.of(""))
            ),
            Map.entry("infinity", new EnchantmentReference(
                    "infinity",
                    1,
                    8,
                    4,
                    1,
                    List.of("bow"),
                    List.of(""),
                    List.of("mending"))
            ),
            Map.entry("knockback", new EnchantmentReference(
                    "knockback",
                    2,
                    2,
                    1,
                    5,
                    List.of("sword"),
                    List.of(""),
                    List.of(""))
            ),
            Map.entry("looting", new EnchantmentReference(
                    "looting",
                    3,
                    4,
                    2,
                    2,
                    List.of("sword"),
                    List.of(""),
                    List.of(""))
            ),
            Map.entry("loyalty", new EnchantmentReference(
                    "loyalty",
                    3,
                    1,
                    1,
                    5,
                    List.of("trident"),
                    List.of(""),
                    List.of("riptide"))
            ),
            Map.entry("luck_of_the_sea", new EnchantmentReference(
                    "luck_of_the_sea",
                    3,
                    4,
                    2,
                    2,
                    List.of("fishing_rod"),
                    List.of(""),
                    List.of(""))
            ),
            Map.entry("lure", new EnchantmentReference(
                    "lure",
                    3,
                    4,
                    2,
                    2,
                    List.of("fishing_rod"),
                    List.of(""),
                    List.of(""))
            ),
            Map.entry("mending", new EnchantmentReference(
                    "mending",
                    1,
                    4,
                    2,
                    2,
                    List.of(""),
                    List.of("pickaxe", "shovel", "axe", "fishing_rod", "helmet", "chestplate", "leggings", "boots", "sword", "bow", "hoe", "shears", "flint_and_steel", "carrot_on_a_stick", "fungus_on_a_stick", "shield", "elytra", "pumpkin", "trident", "crossbow", "brush"),
                    List.of("infinity"))
            ),
            Map.entry("multishot", new EnchantmentReference(
                    "multishot",
                    1,
                    4,
                    2,
                    2,
                    List.of("crossbow"),
                    List.of(""),
                    List.of("piercing"))
            ),
            Map.entry("piercing", new EnchantmentReference(
                    "piercing",
                    4,
                    1,
                    1,
                    10,
                    List.of("crossbow"),
                    List.of(""),
                    List.of("multishot"))
            ),
            Map.entry("power", new EnchantmentReference(
                    "power",
                    5,
                    1,
                    1,
                    10,
                    List.of("bow"),
                    List.of(""),
                    List.of(""))
            ),
            Map.entry("projectile_protection", new EnchantmentReference(
                    "projectile_protection",
                    4,
                    2 ,
                    1,
                    5,
                    List.of("helmet","chestplate","leggings","boots"),
                    List.of(""),
                    List.of("blast_protection","protection","fire_protection"))
            ),
            Map.entry("protection", new EnchantmentReference(
                    "protection",
                    4,
                    1,
                    1,
                    10,
                    List.of("helmet","chestplate","leggings","boots"),
                    List.of(""),
                    List.of("blast_protection","fire_protection","projectile_protection"))
            ),
            Map.entry("punch", new EnchantmentReference(
                    "punch",
                    2,
                    4,
                    2,
                    2,
                    List.of("bow"),
                    List.of(""),
                    List.of(""))
            ),
            Map.entry("quick_charge", new EnchantmentReference(
                    "quick_charge",
                    3,
                    2,
                    1,
                    5,
                    List.of("crossbow"),
                    List.of(""),
                    List.of(""))
            ),
            Map.entry("respiration", new EnchantmentReference(
                    "respiration",
                    3,
                    4,
                    2,
                    2,
                    List.of("helmet"),
                    List.of(""),
                    List.of(""))
            ),
            Map.entry("riptide", new EnchantmentReference(
                    "riptide",
                    3,
                    4,
                    2,
                    2,
                    List.of("trident"),
                    List.of(""),
                    List.of("channeling", "loyalty"))
            ),
            Map.entry("sharpness", new EnchantmentReference(
                    "sharpness",
                    5,
                    1,
                    1,
                    10,
                    List.of("sword"),
                    List.of("axe"),
                    List.of("bane_of_arthropods", "smite"))
            ),
            Map.entry("silk_touch", new EnchantmentReference(
                    "silk_touch",
                    1,
                    8,
                    4,
                    1,
                    List.of("pickaxe", "shovel", "axe", "hoe"),
                    List.of(""),
                    List.of("fortune"))
            ),
            Map.entry("smite", new EnchantmentReference(
                    "smite",
                    5,
                    2,
                    1,
                    5,
                    List.of("sword"),
                    List.of("axe"),
                    List.of("bane_of_arthropods", "sharpness"))
            ),
            Map.entry("soul_speed", new EnchantmentReference(
                    "soul_speed",
                    3,
                    8,
                    4,
                    1,
                    List.of(""),
                    List.of("boots"),
                    List.of(""))
            ),
            Map.entry("sweeping_edge", new EnchantmentReference(
                    "sweeping_edge",
                    3,
                    4,
                    2,
                    2,
                    List.of("sword"),
                    List.of(""),
                    List.of(""))
            ),
            Map.entry("swift_sneak", new EnchantmentReference(
                    "swift_sneak",
                    3,
                    8,
                    4,
                    2,
                    List.of(""),
                    List.of("leggings"),
                    List.of(""))
            ),
            Map.entry("thorns", new EnchantmentReference(
                    "thorns",
                    3,
                    8,
                    4,
                    1,
                    List.of("chestplate"),
                    List.of("leggings","boots","helmet"),
                    List.of(""))
            ),
            Map.entry("unbreaking", new EnchantmentReference(
                    "unbreaking",
                    3,
                    2,
                    1,
                    5,
                    List.of("pickaxe", "shovel", "axe", "fishing_rod", "helmet", "chestplate", "leggings", "boots", "sword", "bow", "hoe", "shears", "flint_and_steel", "carrot_on_a_stick", "fungus_on_a_stick", "shield", "elytra", "pumpkin", "trident", "crossbow", "brush"),
                    List.of(""),
                    List.of(""))
            )
        );
    //</editor-fold>
}
