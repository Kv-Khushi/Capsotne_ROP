package com.restaurants.repository;
import com.restaurants.entities.RestaurantMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantMenuRepository extends JpaRepository<RestaurantMenu, Long> {

    List<RestaurantMenu> findByRestaurantId(Long restaurantId);

    List<RestaurantMenu> findByCategoryId(Long categoryId);

}
