package com.minecrafteiros.EnchantUtils.anvil;

import java.util.ArrayList;

// Wrapper simples contendo o item e o custo total da operação.
// Caso result seja null, trata-se de uma operação impossível.
public class AnvilResponse {

    public AnvilResponse(Item result, Integer cost) {
        this.result = result;
        this.cost = cost;
        treeLevels = new ArrayList<ArrayList<Item>>();
    }

    public AnvilResponse() {
        treeLevels = new ArrayList<ArrayList<Item>>();
    }

    private Item result;
    private Integer cost;

    public void setResult(Item result) {
        this.result = result;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    private ArrayList<ArrayList<Item>> treeLevels;

    public Item getResult() {
        return result;
    }

    public ArrayList<ArrayList<Item>> getTreeLevels() {
        return treeLevels;
    }

    public Integer getCost() {
        return cost;
    }
}
