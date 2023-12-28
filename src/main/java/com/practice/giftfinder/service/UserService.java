package com.practice.giftfinder.service;

import com.practice.giftfinder.enums.CategoryEnum;
import com.practice.giftfinder.model.User;
import com.practice.giftfinder.repository.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void updateRetentionMap(Long userId, List<CategoryEnum> interestedCategories) {
        Map<CategoryEnum, Integer> categoryRetentionMap = getCategoryRetentionMap(userId);
        categoryRetentionMap.keySet().stream().forEach(category -> categoryRetentionMap.put(category, categoryRetentionMap.getOrDefault(category, 0) + 1));

        User user = userRepository.findByUserId(userId);
        interestedCategories.stream().forEach(category -> categoryRetentionMap.put(category, 1));
        user.setCategoryRetentionMap(categoryRetentionMap);
        userRepository.save(user);
    }

    @Transactional
    public Map<CategoryEnum, Integer> getCategoryRetentionMap(Long userId) {
        User user = userRepository.findByUserId(userId);

        return user.getCategoryRetentionMap();
    }

    public User createUser(String userName, Boolean isAdmin) {
        User user = new User();
        user.setUserName(userName);
        user.setIsAdmin(isAdmin);
        user.setCategoryRetentionMap(new HashMap<>());

        return userRepository.save(user);
    }

    public Boolean isUserAdmin(Long userId) {
        User user = userRepository.findByUserId(userId);
        return user.getIsAdmin();
    }
}
