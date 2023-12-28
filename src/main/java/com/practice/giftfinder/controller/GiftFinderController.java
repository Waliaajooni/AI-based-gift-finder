package com.practice.giftfinder.controller;

import com.practice.giftfinder.model.Product;
import com.practice.giftfinder.model.User;
import com.practice.giftfinder.model.dto.ProductDto;
import com.practice.giftfinder.model.dto.UserInputDto;
import com.practice.giftfinder.service.ProductService;

import com.practice.giftfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/finder")
public class GiftFinderController {

    /*
    @Value("${openai.model}")
    private String model;

    @Value(("${openai.api.url}"))
    private String apiURL;

    @Autowired
    private RestTemplate template;

     */

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @PostMapping("/{userId}/add-product")
    public ResponseEntity<Product> addProduct(@PathVariable Long userId, @RequestParam String productName, @RequestParam String color, @RequestParam String category, @RequestParam Integer price) {
        Product product = productService.addProduct(userId, productName, color, category, price);
        if (product == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestParam String userName, @RequestParam Boolean isAdmin) {
        return ResponseEntity.ok(userService.createUser(userName, isAdmin));
    }

    @GetMapping("/predict-products/{userId}")
    public ResponseEntity<List<ProductDto>> predictProducts(@PathVariable Long userId) {
        return ResponseEntity.ok(productService.predictResults(userId));
    }

    @GetMapping("/{userId}/search")
    public ResponseEntity<List<ProductDto>> getSearchResults(@PathVariable Long userId, @RequestParam String searchPhrase) {
        return ResponseEntity.ok(productService.search(userId, searchPhrase));
    }

    @GetMapping("/{userId}/get-recommendations")
    public ResponseEntity<List<ProductDto>> getRecommendations(@RequestBody UserInputDto userInput) {
        List<ProductDto> products  = productService.getRecommendedProductsBasedOnInterests(userInput);
        return ResponseEntity.ok(products);
    }
}
