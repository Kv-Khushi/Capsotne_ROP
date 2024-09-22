package com.restaurants.repository;

import com.restaurants.entities.Restaurant;
import com.restaurants.entities.RestaurantMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing and manipulating {@link Restaurant} entities.
 */
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    /**
     * Finds an {@link RestaurantMenu} entity by the given restaurant ID.
     *
     * @param restaurantId the ID of the restaurant to find the menu for
     * @return an {@link Optional} containing the {@link RestaurantMenu} entity if found, or an empty {@link Optional} if not
     */
    Optional<RestaurantMenu> findByRestaurantId(Long restaurantId);


    /**
     * Finds a list of {@link Restaurant} entities by the given user ID.
     *
     * @param userId the ID of the user to find the restaurants for
     * @return a list of {@link Restaurant} entities associated with the given user ID
     */
    List<Restaurant> findByUserId(Long userId);


    /**
     * Checks if a restaurant with the given name exists in the database, ignoring case.
     *
     * @param restaurantName the name of the restaurant to check for existence
     * @return true if a restaurant with the specified name exists, false otherwise
     */
    boolean existsByRestaurantNameIgnoreCase(String restaurantName);
}
