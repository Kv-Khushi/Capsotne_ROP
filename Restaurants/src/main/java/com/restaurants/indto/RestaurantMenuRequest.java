package com.restaurants.indto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Data transfer object for creating or updating a restaurant menu item.
 */
@Data
public class RestaurantMenuRequest {

    /**
     * The name of the menu item.
     */
    private String itemName;

    /**
     * The price of the menu item.
     */
    private Double price;

    /**
     * A description of the menu item.
     */
    private String description;

    /**
     * Indicates whether the menu item is vegetarian or non-vegetarian.
     */
    private Boolean vegNonVeg;

    /**
     * The ID of the category to which the menu item belongs.
     */
    private Long categoryId;

    /**
     * The ID of the restaurant that offers the menu item.
     */
    private Long restaurantId;
    private MultipartFile imageUrl;
}
