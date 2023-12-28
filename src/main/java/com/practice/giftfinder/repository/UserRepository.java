package com.practice.giftfinder.repository;

import com.practice.giftfinder.enums.CategoryEnum;
import com.practice.giftfinder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT usr.categoryRetentionMap FROM User usr WHERE usr.userId = userId")
    Map<CategoryEnum, Integer> findCategoryRetentionMap(@Param("userId") Long userId);

    User findByUserId(Long userId);
}
