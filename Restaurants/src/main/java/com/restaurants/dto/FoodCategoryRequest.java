package com.restaurants.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

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
    @NotBlank(message = "Category name should not be null.")

    private String categoryName;
}
