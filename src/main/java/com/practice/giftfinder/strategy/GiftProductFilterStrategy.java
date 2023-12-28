package com.practice.giftfinder.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.giftfinder.enums.CategoryEnum;
import com.practice.giftfinder.model.Product;
import com.practice.giftfinder.model.dto.ProductDto;
import com.practice.giftfinder.model.dto.UserInputDto;
import com.practice.giftfinder.repository.ProductRepository;
import com.practice.giftfinder.strategy.interfaces.ProductFilterBudgetBasedStrategy;
import com.practice.giftfinder.strategy.interfaces.ProductFilterStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GiftProductFilterStrategy<T> implements ProductFilterBudgetBasedStrategy<T> {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ObjectMapper mapper;

    @Override
    public List<ProductDto> getProductsBasedOnBudgetAndCategory(UserInputDto userInput, List<CategoryEnum> interestedCategories) {
        List<Product> products = productRepository.findByRelevantCategoryAndLessThanBudget(interestedCategories.get(0), userInput.getBudget());
        products.addAll(productRepository.findByRelevantCategoryAndLessThanBudget(interestedCategories.get(1), userInput.getBudget()));

        List<ProductDto> productDtos = products.stream().map(prod -> mapper.convertValue(prod, ProductDto.class)).collect(Collectors.toList());

        return productDtos;
    }
}
