package com.practice.giftfinder.template;

import com.practice.giftfinder.enums.CategoryEnum;
import com.practice.giftfinder.model.dto.ProductDto;
import com.practice.giftfinder.model.dto.UserInputDto;

import java.util.List;

public abstract class GiftProductTemplate<T> {
    abstract List<ProductDto> getProductsBasedOnBudgetAndCategory(T userInput, List<CategoryEnum> interestedCategories);
    abstract List<CategoryEnum> getCategories(T userInput);
    abstract List<ProductDto> getProductsFromGpt(List<ProductDto> products, T userInput);

    public List<ProductDto> getGptProducts(T input) {
        List<CategoryEnum> interestedCategories = getCategories(input);
        List<ProductDto> filteredProducts = getProductsBasedOnBudgetAndCategory(input, interestedCategories);
        List<ProductDto> gptProducts = getProductsFromGpt(filteredProducts, input);

        return gptProducts;
    }
}
