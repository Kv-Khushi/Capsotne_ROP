package com.restaurants.dto;

import lombok.Data;

/**
 * Data transfer object representing the response for a food category.
 */
@Data
public class FoodCategoryResponse {

    /**
     * The unique identifier of the food category.
     */
    private Long categoryId;

    /**
     * The unique identifier of the restaurant to which the category belongs.
     */
    private Long restaurantId;

    /**
     * The name of the food category.
     */
    private String categoryName;
}
