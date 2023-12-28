package com.practice.giftfinder.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.giftfinder.enums.CategoryEnum;
import com.practice.giftfinder.model.Product;
import com.practice.giftfinder.model.dto.ProductDto;
import com.practice.giftfinder.repository.ProductRepository;
import com.practice.giftfinder.service.UserService;
import com.practice.giftfinder.strategy.interfaces.ProductFilterStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchProductFilterStrategy implements ProductFilterStrategy {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Autowired
    ObjectMapper mapper;

    @Override
    public List<ProductDto> getProductsBasedOnCategory(Long userId, List<CategoryEnum> interestedCategories) {
        List<Product> products = interestedCategories.stream().map(category -> productRepository.findByRelevantCategory(category)).flatMap(List::stream).collect(Collectors.toList());
        userService.updateRetentionMap(userId, interestedCategories);
        return products.stream().map(prod -> mapper.convertValue(prod, ProductDto.class)).collect(Collectors.toList());
    }
}
