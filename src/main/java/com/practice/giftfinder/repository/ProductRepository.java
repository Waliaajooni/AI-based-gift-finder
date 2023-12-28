package com.practice.giftfinder.repository;

import com.practice.giftfinder.enums.CategoryEnum;
import com.practice.giftfinder.enums.ColorEnum;
import com.practice.giftfinder.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByRelevantCategory(CategoryEnum categoryEnum);
    Product findByProdId(Long prodId);
    List<Product> findByRelevantCategoryAndColor(CategoryEnum categoryEnum, ColorEnum colorEnum);
    @Query("SELECT p FROM Product p WHERE p.relevantCategory = :interestedCategory AND p.price < :budget")
    List<Product> findByRelevantCategoryAndLessThanBudget(@Param("interestedCategory") CategoryEnum interestedCategory, @Param("budget") Integer budget);
}
