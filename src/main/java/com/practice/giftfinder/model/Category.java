package com.practice.giftfinder.model;

import com.practice.giftfinder.enums.CategoryEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long categoryId;
    private CategoryEnum categoryEnum;
    public Category(CategoryEnum categoryEnum) {
        this.categoryEnum = categoryEnum;
    }
}
