package com.practice.giftfinder.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.giftfinder.model.Product;
import com.practice.giftfinder.model.dto.ProductDto;
import com.practice.giftfinder.model.gpt.GptResponse;
import com.practice.giftfinder.repository.ProductRepository;
import com.practice.giftfinder.service.Gpt3Service;
import com.practice.giftfinder.strategy.interfaces.ProductStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchProductStrategy implements ProductStrategy<String> {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private Gpt3Service gptService;

    private final String PROMPT = "%s : these are the available products filter out closest products according to the search phrase '%s' , just give the prod Ids comma separated nothing else";

    @Override
    public List<ProductDto> getProductsFromGpt(List<ProductDto> products, String searchPhrase) {
        String prompt = String.format(PROMPT, products, searchPhrase);
        GptResponse productsResponse = gptService.getGPTResponseForPrompt(prompt);
        List<String> prodIds = Arrays.asList(productsResponse.getChoices().get(0).getMessage().getContent().split(", "));

        List<Product> interestedProducts = prodIds.stream().map(prodId -> productRepository.findByProdId(Long.parseLong(prodId))).collect(Collectors.toList());

        return interestedProducts.stream().map(product -> mapper.convertValue(product, ProductDto.class)).collect(Collectors.toList());
    }
}
