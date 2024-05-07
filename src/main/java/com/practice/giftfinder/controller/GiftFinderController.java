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
@RequestMapping("/api")
public class GiftFinderController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestParam Long userId, @RequestBody ProductDto productDto) {
        Product product = productService.addProduct(userId, productDto);
        if (product == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestParam String userName, @RequestParam Boolean isAdmin) {
        return ResponseEntity.ok(userService.createUser(userName, isAdmin));
    }

    @GetMapping("/predictions/users/{userId}")
    public ResponseEntity<List<ProductDto>> predictProducts(@PathVariable Long userId) {
        return ResponseEntity.ok(productService.predictResults(userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> getSearchResults(@RequestParam Long userId, @RequestParam String searchPhrase) {
        return ResponseEntity.ok(productService.search(userId, searchPhrase));
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<ProductDto>> getRecommendations(@RequestBody UserInputDto userInput) {
        List<ProductDto> products  = productService.getRecommendedProductsBasedOnInterests(userInput);
        return ResponseEntity.ok(products);
    }
}
