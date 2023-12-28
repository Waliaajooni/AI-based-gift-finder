package com.practice.giftfinder.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.giftfinder.enums.CategoryEnum;
import com.practice.giftfinder.enums.ColorEnum;
import com.practice.giftfinder.model.Product;
import com.practice.giftfinder.model.dto.ProductDto;
import com.practice.giftfinder.model.dto.UserInputDto;
import com.practice.giftfinder.model.gpt.GptResponse;
import com.practice.giftfinder.repository.ProductRepository;
import com.practice.giftfinder.strategy.*;
import com.practice.giftfinder.strategy.interfaces.CategoryStrategy;
import com.practice.giftfinder.strategy.interfaces.ProductFilterBudgetBasedStrategy;
import com.practice.giftfinder.strategy.interfaces.ProductFilterStrategy;
import com.practice.giftfinder.strategy.interfaces.ProductStrategy;
import com.practice.giftfinder.template.GiftProductTemplate;
import com.practice.giftfinder.template.GptProductTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {

    /*
    @Value("${openai.model}")
    private String model;

    @Value(("${openai.api.url}"))
    private String apiURL;

    @Autowired
    private RestTemplate template;

     */

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private Gpt3Service gptService;

    public Product addProduct(Long userId, String name, String color, String categoryName, Integer price) {
        if (!userService.isUserAdmin(userId)) return null;

        String prompt = "describe " + color + " color "  + name + " in two lines";
        GptResponse chatGptResponse = gptService.getGPTResponseForPrompt(prompt);
        String description = chatGptResponse.getChoices().get(0).getMessage().getContent();

        //String description = "cba";
        Product product = new Product();
        product.setName(name);
        product.setColor(ColorEnum.valueOf(color));
        product.setDescription(description);
        product.setRelevantCategory(CategoryEnum.valueOf(categoryName));
        product.setPrice(price);

        return productRepository.save(product);
    }

    //search api - category, products
    // recommend based on user recent (1 month) history

    @SuppressWarnings("unchecked")
    public List<ProductDto> search(Long userId, String searchPhrase) {
        CategoryStrategy<String> searchCategoryStrategy = applicationContext.getBean(SearchCategoryStrategy.class);
        ProductStrategy<String> searchProductStrategy = applicationContext.getBean(SearchProductStrategy.class);
        ProductFilterStrategy productFilterStrategy = applicationContext.getBean(SearchProductFilterStrategy.class);

        GptProductTemplate<String> gptProductTemplate = (GptProductTemplate<String>) applicationContext.getBean("gptProductTemplateImpl", productFilterStrategy, searchProductStrategy, searchCategoryStrategy);

        return gptProductTemplate.getGptProducts(userId, searchPhrase);
    }

    public List<ProductDto> predictResults(Long userId) {
        Map<CategoryEnum, Integer> categoryRetentionMap = userService.getCategoryRetentionMap(userId);
        List<List<Product>> products = new ArrayList<>();

        int[] previousSearchCount = {30};
        categoryRetentionMap.keySet().stream().forEach(category -> {
            if (categoryRetentionMap.get(category) <= 30 && categoryRetentionMap.get(category) < previousSearchCount[0]) {
                products.add(0, productRepository.findByRelevantCategory(category));
            }
            else if (categoryRetentionMap.get(category) <= 30) {
                products.add(productRepository.findByRelevantCategory(category));
            }
            previousSearchCount[0] = categoryRetentionMap.get(category);
        });

        List<ProductDto> predictedProducts = products.stream().flatMap(List::stream).map(product -> mapper.convertValue(product, ProductDto.class)).collect(Collectors.toList());

        return predictedProducts.subList(0, Math.min(7, predictedProducts.size()));
    }

    public List<ProductDto> getRecommendedProductsBasedOnInterests(UserInputDto userInput) {
        CategoryStrategy<UserInputDto> giftCategoryStrategy = applicationContext.getBean(GiftCategoryStrategy.class);
        ProductStrategy<UserInputDto> giftProductStrategy = applicationContext.getBean(GiftProductStrategy.class);
        ProductFilterBudgetBasedStrategy<UserInputDto> productFilterStrategy = applicationContext.getBean(GiftProductFilterStrategy.class);

        GiftProductTemplate<UserInputDto> gptGiftProductTemplate = (GiftProductTemplate<UserInputDto>) applicationContext.getBean("giftProductTemplateImpl", productFilterStrategy, giftProductStrategy, giftCategoryStrategy);

        return gptGiftProductTemplate.getGptProducts(userInput);
    }
}
