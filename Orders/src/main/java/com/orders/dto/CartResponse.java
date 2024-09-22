package com.orders.dto;


import lombok.Data;



/**
 * Data Transfer Object (DTO) for sending cart details as a response.
 * This class contains information about the cart such as the cart ID,
 * food item ID, quantity, and price per item.
 * <p>
 * Lombok's {@code @Data} annotation is used to automatically generate getters,
 * setters, equals, hashCode, and toString methods.
 * </p>
 */
@Data
public class CartResponse {

    /**
     * Unique identifier for the cart.
     */
    private Long cartId;

    /**
     * Unique identifier for the food item associated with this cart.
     */
    private Long foodItemId;

    /**
     * Quantity of the food item in the cart.
     */
    private Integer quantity;

    /**
     * Price per unit of the food item.
     */
    private Double pricePerItem;

}
