package com.restaurants.outdto;

import lombok.Data;
@Data

public class RestaurantMenuResponse {
    private Long itemId;
    private String itemName;
    private Double price;
    private String description;
    private Boolean vegNonVeg;
    private Long categoryId;
    private Long restaurantId;
    private String imageUrl;
}
