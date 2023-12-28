package com.practice.giftfinder.model;

import com.practice.giftfinder.enums.CategoryEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Map;

@Data
@Entity
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userName;
    private Boolean isAdmin;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "category_retention_map",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "days")
    private Map<CategoryEnum, Integer> categoryRetentionMap;
    //            category    no of searches
}
