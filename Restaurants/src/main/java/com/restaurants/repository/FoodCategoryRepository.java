package com.restaurants.repository;

import com.restaurants.entities.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * Repository interface for accessing and manipulating {@link FoodCategory} entities.
 */
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {

 /**
  * Finds a list of {@link FoodCategory} entities by the given restaurant ID.
  *
  * @param restaurantId the ID of the restaurant to find food categories for
  * @return a list of {@link FoodCategory} entities associated with the specified restaurant ID
  */
 List<FoodCategory> findByRestaurantId(Long restaurantId);

 /**
  * Finds a {@link FoodCategory} entity by the given restaurant ID and category name.
  *
  * @param restaurantId the ID of the restaurant
  */
 boolean existsByRestaurantIdAndCategoryNameIgnoreCase(Long restaurantId, String categoryName);

}
