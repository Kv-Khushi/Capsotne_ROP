package com.restaurants.indto;

import lombok.Data;

@Data
public class RestaurantMenuRequest {
    private String itemName;
    private Double price;
    private String description;
    private Boolean vegNonVeg;
    private Long categoryId;
    private Long restaurantId;
    private String imageUrl;
}
