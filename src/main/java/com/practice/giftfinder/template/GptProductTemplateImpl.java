package com.practice.giftfinder.template;

import com.practice.giftfinder.enums.CategoryEnum;
import com.practice.giftfinder.model.dto.ProductDto;
import com.practice.giftfinder.strategy.SearchCategoryStrategy;
import com.practice.giftfinder.strategy.SearchProductFilterStrategy;
import com.practice.giftfinder.strategy.SearchProductStrategy;
import com.practice.giftfinder.strategy.interfaces.CategoryStrategy;
import com.practice.giftfinder.strategy.interfaces.ProductFilterStrategy;
import com.practice.giftfinder.strategy.interfaces.ProductStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("gptProductTemplateImpl")
@Lazy
@Scope("prototype")
public class GptProductTemplateImpl<T> extends GptProductTemplate<T>{

    private final ProductFilterStrategy productFilterStrategy;
    private final ProductStrategy<T> productStrategy;
    private final CategoryStrategy<T> categoryStrategy;

    @Autowired
    public GptProductTemplateImpl(ProductFilterStrategy productFilterStrategy, ProductStrategy<T> productStrategy, CategoryStrategy<T> categoryStrategy) {
        this.productFilterStrategy = productFilterStrategy;
        this.productStrategy = productStrategy;
        this.categoryStrategy = categoryStrategy;
    }

    @Override
    List<ProductDto> getSearchedCategoriesFilteredProducts(Long userId, List<CategoryEnum> categories) {
        return productFilterStrategy.getProductsBasedOnCategory(userId, categories);
    }

    @Override
    List<CategoryEnum> getCategoriesBasedOnUserInput(T userInput) {
        return categoryStrategy.getCategories(userInput);
    }

    @Override
    List<ProductDto> getProductsFromGpt(T userInput, List<ProductDto> products) {
        return productStrategy.getProductsFromGpt(products, userInput);
    }
}
