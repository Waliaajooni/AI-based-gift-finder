package com.practice.giftfinder.strategy.interfaces;

import com.practice.giftfinder.enums.CategoryEnum;
import com.practice.giftfinder.model.dto.ProductDto;

import java.util.List;

public interface ProductFilterStrategy {
    List<ProductDto> getProductsBasedOnCategory(Long userId, List<CategoryEnum> interestedCategories);
}
