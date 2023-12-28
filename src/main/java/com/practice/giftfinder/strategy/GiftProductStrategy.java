package com.practice.giftfinder.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.giftfinder.model.Product;
import com.practice.giftfinder.model.dto.ProductDto;
import com.practice.giftfinder.model.dto.UserInputDto;
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
public class GiftProductStrategy implements ProductStrategy<UserInputDto> {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private Gpt3Service gptService;

    private final String PROMPT = "%s : From these products filter out most relevant 5 products for my '%s', who loves '%s', for the occasion of %s, just give prod Ids comma separated, nothing else";

    @Override
    public List<ProductDto> getProductsFromGpt(List<ProductDto> products, UserInputDto userInput) {
        String prompt = String.format(PROMPT, products, userInput.getRelation(), userInput.getPersonalInterest(),userInput.getOccasion());

        GptResponse productsResponse = gptService.getGPTResponseForPrompt(prompt);

        List<String> prodIds = Arrays.asList(productsResponse.getChoices().get(0).getMessage().getContent().split(", "));
        List<Product> gptProducts = prodIds.stream().map(prodId -> productRepository.findByProdId(Long.parseLong(prodId))).collect(Collectors.toList());

        List<ProductDto> productDtos = gptProducts.stream().map(prod -> mapper.convertValue(prod, ProductDto.class)).collect(Collectors.toList());

        return productDtos;

    }
}
