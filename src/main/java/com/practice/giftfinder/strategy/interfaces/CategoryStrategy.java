package com.practice.giftfinder.strategy.interfaces;

import com.practice.giftfinder.enums.CategoryEnum;

import java.util.List;

public interface CategoryStrategy<T> {

    List<CategoryEnum> getCategories(T input);
}
