package com.practice.giftfinder.template;

import com.practice.giftfinder.enums.CategoryEnum;
import com.practice.giftfinder.model.dto.ProductDto;
import com.practice.giftfinder.model.dto.UserInputDto;
import com.practice.giftfinder.strategy.interfaces.CategoryStrategy;
import com.practice.giftfinder.strategy.interfaces.ProductFilterBudgetBasedStrategy;
import com.practice.giftfinder.strategy.interfaces.ProductFilterStrategy;
import com.practice.giftfinder.strategy.interfaces.ProductStrategy;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("giftProductTemplateImpl")
@Lazy
@Scope("prototype")
public class GiftProductTemplateImpl<T> extends GiftProductTemplate<T> {

    private final ProductFilterBudgetBasedStrategy<T> productFilterStrategy;
    private final ProductStrategy<T> productStrategy;
    private final CategoryStrategy<T> categoryStrategy;

    public GiftProductTemplateImpl(ProductFilterBudgetBasedStrategy productFilterStrategy, ProductStrategy<T> productStrategy, CategoryStrategy<T> categoryStrategy) {
        this.productFilterStrategy = productFilterStrategy;
        this.productStrategy = productStrategy;
        this.categoryStrategy = categoryStrategy;
    }

    @Override
    List<ProductDto> getProductsBasedOnBudgetAndCategory(T userInput, List<CategoryEnum> interestedCategories) {
        return productFilterStrategy.getProductsBasedOnBudgetAndCategory((UserInputDto) userInput, interestedCategories);
    }

    @Override
    List<CategoryEnum> getCategories(T userInput) {
        return categoryStrategy.getCategories(userInput);
    }

    @Override
    List<ProductDto> getProductsFromGpt(List<ProductDto> products, T userInput) {
        return productStrategy.getProductsFromGpt(products, userInput);
    }
}
