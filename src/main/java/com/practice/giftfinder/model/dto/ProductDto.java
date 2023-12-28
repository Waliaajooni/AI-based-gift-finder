package com.practice.giftfinder.model.dto;

import com.practice.giftfinder.enums.CategoryEnum;
import com.practice.giftfinder.enums.ColorEnum;
import lombok.Data;

@Data
public class ProductDto {
    private Long prodId;
    private String name;
    private String description;
    private Integer price;
    private ColorEnum color;
    private CategoryEnum relevantCategory;

    @Override
    public String toString() {
        return "Product{" +
                "prodId=" + prodId +
                ", name='" + name + '\'' +
                '}';
    }
}
