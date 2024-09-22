package com.orders.dto;


import lombok.Data;

/**
 * Data Transfer Object (DTO) representing the response for a restaurant.
 * This class contains basic information about a restaurant, such as its unique ID and name.
 * <p>
 * Lombok's {@code @Data} annotation is used to automatically generate
 * getters, setters, equals, hashCode, and toString methods.
 * </p>
 */
@Data
public class RestaurantResponse {
    /**
     * The unique ID of the restaurant.
     */
    private Long restaurantId;

    /**
     * The name of the restaurant.
     */
    private String restaurantName;

}
