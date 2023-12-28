package com.practice.giftfinder.model.dto;

import com.practice.giftfinder.enums.CategoryEnum;
import lombok.Data;

import java.util.Map;

@Data
public class UserDto {
    private Long userId;
    private String userName;
    Boolean isAdmin;
    private Map<CategoryEnum, Integer> categoryRetentionMap;
}
