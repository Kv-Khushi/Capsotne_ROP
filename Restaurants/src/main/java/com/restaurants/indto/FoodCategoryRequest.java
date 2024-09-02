package com.restaurants.indto;

import lombok.Data;

/**
 * Data transfer object for creating or updating a food category.
 */
@Data
public class FoodCategoryRequest {

    /**
     * The ID of the restaurant to which the food category belongs.
     */
    private Long restaurantId;

    /**
     * The name of the food category.
     */
    private String categoryName;
}
