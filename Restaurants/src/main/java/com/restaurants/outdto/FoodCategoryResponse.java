package com.restaurants.outdto;

import lombok.Data;

@Data
public class FoodCategoryResponse {
    private Long categoryId;
    private Long restaurantId;
    private String categoryName;
}
