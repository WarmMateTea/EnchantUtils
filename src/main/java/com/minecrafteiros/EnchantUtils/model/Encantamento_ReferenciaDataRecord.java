package com.minecrafteiros.EnchantUtils.model;

import java.util.Set;

public record Encantamento_ReferenciaDataRecord(String string_id, String display_name, String description, Boolean is_treasure, Integer max_level, Integer item_multiplier, Integer book_multiplier, Integer enchantment_weight, Set<Item_Referencia> primary_items, Set<Item_Referencia> secondary_items) {
}
