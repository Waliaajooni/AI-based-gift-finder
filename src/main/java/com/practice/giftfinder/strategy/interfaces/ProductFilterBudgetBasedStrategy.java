package com.practice.giftfinder.strategy.interfaces;

import com.practice.giftfinder.enums.CategoryEnum;
import com.practice.giftfinder.model.dto.ProductDto;
import com.practice.giftfinder.model.dto.UserInputDto;

import java.util.List;

public interface ProductFilterBudgetBasedStrategy<T> {
    List<ProductDto> getProductsBasedOnBudgetAndCategory(UserInputDto userInputDto, List<CategoryEnum> interestedCategories);
}
