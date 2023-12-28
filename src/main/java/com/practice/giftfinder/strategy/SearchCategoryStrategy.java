package com.practice.giftfinder.strategy;

import com.practice.giftfinder.enums.CategoryEnum;
import com.practice.giftfinder.model.gpt.GptResponse;
import com.practice.giftfinder.service.Gpt3Service;
import com.practice.giftfinder.strategy.interfaces.CategoryStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchCategoryStrategy implements CategoryStrategy<String> {

    @Autowired
    private Gpt3Service gptService;

    final String PROMPT = "%s : these are the available categories give me a very least number of really closest categories to the search phrase '%s' , just give the  category names comma separated nothing else";

    @Override
    public List<CategoryEnum> getCategories(String searchPhrase) {
        List<CategoryEnum> categoriesEnums = Arrays.stream(CategoryEnum.values()).toList();
        String categories = categoriesEnums.stream().map(CategoryEnum::name).reduce((category1, category2) -> category1 + " , " + category2).orElse("");

        String prompt = String.format(PROMPT, categories, searchPhrase);

        GptResponse categoriesResponse = gptService.getGPTResponseForPrompt(prompt);

        List<String> recommendedCategories = Arrays.asList(categoriesResponse.getChoices().get(0).getMessage().getContent().split(", "));
        List<CategoryEnum> interestedCategories = recommendedCategories.stream().map(category -> CategoryEnum.valueOf(category)).collect(Collectors.toList());

        return interestedCategories;
    }
}
