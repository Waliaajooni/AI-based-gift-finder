package com.practice.giftfinder.strategy.interfaces;

import com.practice.giftfinder.model.dto.ProductDto;

import java.util.List;

public interface ProductStrategy<T> {

    List<ProductDto> getProductsFromGpt(List<ProductDto> products,T searchPhrase);
}
