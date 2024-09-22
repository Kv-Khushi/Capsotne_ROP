package com.orders.dto;

import com.orders.entities.Cart;
import lombok.Data;

import java.util.List;



/**
 * Data Transfer Object (DTO) for handling order requests.
 * This class is used to encapsulate the data required to place an order,
 * including the user information, restaurant, address, and the list of cart items.
 * <p>
 * Lombok's {@code @Data} annotation is used to automatically generate
 * getters, setters, equals, hashCode, and toString methods.
 * </p>
 */
@Data
public class OrderRequest {

    /**
     * The ID of the user placing the order.
     */
    private Long userId;

    /**
     * The ID of the restaurant from which the order is being placed.
     */
    private Long restaurantId;

    /**
     * The ID of the address where the order should be delivered.
     */
    private Long addressId;


    /**
     * A list of cart items that are included in the order.
     * Each item represents a food item selected by the user.
     */
    private List<Cart> items;

}
