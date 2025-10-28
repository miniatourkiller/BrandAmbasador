package com.gym.GoldenGym.dtos;

import com.gym.GoldenGym.entities.Category;

import lombok.Data;
@Data
public class CategoryDto {
    private String name;
    private String iconUrl;
    
    public Category toCategory() {
        Category category = new Category();
        category.setName(name);
        category.setIconUrl(iconUrl);
        return category;
    }
}
