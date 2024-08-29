package com.restaurants.repository;

import com.restaurants.entities.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {

 List<FoodCategory> findByRestaurantId(Long restaurantId);

}
