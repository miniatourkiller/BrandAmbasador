package com.gym.GoldenGym.dtos;

import com.gym.GoldenGym.entities.Category;
import com.gym.GoldenGym.entities.Item;

import lombok.Data;

@Data
public class ItemDto {
    private Long categoryId;
    private String itemName;
    private String itemDescription;
    private String manufacturer;

    public Item toItem(Category category) {
        Item item = new Item();
        item.setCategory(category);
        item.setItemName(this.itemName);
        item.setItemDescription(this.itemDescription);
        item.setManufacturer(this.manufacturer);
        return item;
    }
}
