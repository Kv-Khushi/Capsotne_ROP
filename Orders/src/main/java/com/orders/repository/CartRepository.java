package com.orders.repository;

import com.orders.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(Long userId);


    // Find an item in the cart by userId and foodItemId (for updating quantity)
    Optional<Cart> findByUserIdAndFoodItemId(Long userId, Long foodItemId);

    void deleteByUserIdAndFoodItemId(Long userId, Long foodItemId);

    Cart save(Cart cart);



}
