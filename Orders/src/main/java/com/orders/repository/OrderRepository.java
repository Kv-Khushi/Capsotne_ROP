package com.orders.repository;

import com.orders.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * Repository interface for handling {@link Order} entities.
 */

public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * Finds a list of orders by the given user ID.
     *
     * @param userId the ID of the user
     * @return a list of {@link Order} entities associated with the user ID
     */
    List<Order> findByUserId(Long userId);
    List<Order> findByRestaurantId(Long restaurantId);
}

