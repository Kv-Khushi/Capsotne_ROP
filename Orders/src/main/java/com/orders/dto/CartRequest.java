package com.orders.dto;


import lombok.Data;
/**
 * A request DTO for handling cart-related operations.
 */
@Data
public class CartRequest {
    /**
     * The ID of the user.
     */
    private Long userId;

    /**
     * The ID of the restaurant.
     */
    private Long restaurantId;

    /**
     * The ID of the cart.
     */
    private Long cartId;

    /**
     * The quantity of the items.
     */
    private Integer quantity;

    /**
     * The price per item.
     */
    private Double pricePerItem;

    /**
     * The ID of the food item.
     */
    private Long foodItemId;

}
