package com.restaurants.repository;

import com.restaurants.entities.RestaurantMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for accessing and manipulating {@link RestaurantMenu} entities.
 */
public interface RestaurantMenuRepository extends JpaRepository<RestaurantMenu, Long> {

    /**
     * Finds a list of {@link RestaurantMenu} entities by the given restaurant ID.
     *
     * @param restaurantId the ID of the restaurant to find menu items for
     * @return a list of {@link RestaurantMenu} entities associated with the specified restaurant ID
     */
    List<RestaurantMenu> findByRestaurantId(Long restaurantId);

    /**
     * Checks if a {@link RestaurantMenu} entity with the given restaurant ID and item name exists.
     *
     * @param restaurantId the ID of the restaurant
     * @param itemName     the name of the menu item
     * @return true if the menu item exists for the given restaurant, false otherwise
     */
    boolean existsByRestaurantIdAndItemName(Long restaurantId, String itemName);

    /**
     * Finds a list of {@link RestaurantMenu} entities by the given category ID.
     *
     * @param categoryId the ID of the category to find menu items for
     * @return a list of {@link RestaurantMenu} entities associated with the specified category ID
     */
    List<RestaurantMenu> findByCategoryId(Long categoryId);

    /**
     * Checks if a {@link RestaurantMenu} entity with the given restaurant ID and item name (ignoring case) exists.
     *
     * @param restaurantId the ID of the restaurant
     * @param itemName     the name of the menu item (case insensitive)
     * @return true if the menu item exists for the given restaurant, false otherwise
     */
    boolean existsByRestaurantIdAndItemNameIgnoreCase(Long restaurantId, String itemName);

}
