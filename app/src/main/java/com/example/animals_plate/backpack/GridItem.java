package com.example.animals_plate.backpack;

public class GridItem {
    private int imageResource;
    private String itemName;

    public GridItem(int imageResource, String itemName) {
        this.imageResource = imageResource;
        this.itemName = itemName;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getItemName() {
        return itemName;
    }
}
