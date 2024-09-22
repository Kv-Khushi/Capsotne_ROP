package com.orders.repository;

import com.orders.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Cart entity operations.
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * Finds a list of cart items by the given user ID.
     *
     * @param userId the ID of the user
     * @return a list of Cart entities
     */
    List<Cart> findByUserId(Long userId);

    /**
     * Finds a cart item by user ID and food item ID (used for updating quantity).
     *
     * @param userId the ID of the user
     * @param foodItemId the ID of the food item
     * @return an Optional containing the Cart entity if found
     */
    Optional<Cart> findByUserIdAndFoodItemId(Long userId, Long foodItemId);



    /**
     * Saves the cart entity to the database.
     *
     * @param cart the Cart entity to save
     * @return the saved Cart entity
     */
     Cart save(Cart cart);



}
