package com.orders.dto;

import lombok.Data;



/**
 * Data Transfer Object (DTO) representing the response for a restaurant menu item.
 * This class contains information about a specific item on a restaurant's menu,
 * including details like the item's name, price, description, and its categorization.
 * <p>
 * Lombok's {@code @Data} annotation is used to automatically generate
 * getters, setters, equals, hashCode, and toString methods.
 * </p>
 */
@Data
public class RestaurantMenuResponse {

    /**
     * The unique ID of the menu item.
     */
    private Long itemId;

    /**
     * The name of the menu item.
     */
    private String itemName;

    /**
     * The price of the menu item.
     */
    private Double price;

    /**
     * A brief description of the menu item.
     */
    private String description;

    /**
     * Indicates whether the menu item is vegetarian or non-vegetarian.
     * A value of {@code true} means vegetarian, and {@code false} means non-vegetarian.
     */
    private Boolean vegNonVeg;

    /**
     * The unique ID of the category to which this menu item belongs.
     */
    private Long categoryId;

    /**
     * The unique ID of the restaurant offering this menu item.
     */
    private Long restaurantId;


}
