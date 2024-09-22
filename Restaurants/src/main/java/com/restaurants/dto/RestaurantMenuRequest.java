package com.restaurants.dto;

import lombok.Data;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Data transfer object for creating or updating a restaurant menu item.
 */
@Data
public class RestaurantMenuRequest {

    /**
     * The name of the menu item.
     */
    @NotBlank(message = "Item name should not be null.")
    private String itemName;

    /**
     * The price of the menu item.
     */
    @NotNull(message = "Price is required.")
    @Positive(message = "Price should be a positive number.")
    @DecimalMin(value = "0.01", message = "Price should be greater than zero.")
    private Double price;

    /**
     * A description of the menu item.
     */
    @NotBlank(message = "Description should not be blank.")
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

}
