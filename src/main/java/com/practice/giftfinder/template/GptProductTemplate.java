package com.practice.giftfinder.template;

import com.practice.giftfinder.enums.CategoryEnum;
import com.practice.giftfinder.model.dto.ProductDto;

import java.util.List;

public abstract class GptProductTemplate<T> {

    abstract List<ProductDto> getSearchedCategoriesFilteredProducts(Long userId, List<CategoryEnum> categories);

    abstract List<CategoryEnum> getCategoriesBasedOnUserInput(T userInput);

    abstract List<ProductDto> getProductsFromGpt(T userInput, List<ProductDto> products);

    public List<ProductDto> getGptProducts(Long userId, T input) {
        List<CategoryEnum> interestedCategories = getCategoriesBasedOnUserInput(input);
        List<ProductDto> filteredProducts = getSearchedCategoriesFilteredProducts(userId, interestedCategories);
        List<ProductDto> gptProducts = getProductsFromGpt(input, filteredProducts);

        return gptProducts;
    }
}
