package com.practice.giftfinder.model;

import com.practice.giftfinder.enums.CategoryEnum;
import com.practice.giftfinder.enums.ColorEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "prod_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
