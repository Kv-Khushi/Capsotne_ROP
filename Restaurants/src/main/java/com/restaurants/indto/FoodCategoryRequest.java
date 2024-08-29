package com.restaurants.indto;

import lombok.Data;

@Data
public class FoodCategoryRequest {
    private Long restaurantId;
    private String categoryName;
}
