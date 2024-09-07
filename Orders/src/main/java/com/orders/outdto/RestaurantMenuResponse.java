package com.orders.outdto;

import lombok.Data;



/**
 * Data transfer object representing the response for a restaurant menu item.
 */
@Data
public class RestaurantMenuResponse {

    private Long itemId;
    private String itemName;
    private Double price;
    private String description;
    private Boolean vegNonVeg;
    private Long categoryId;
    private Long restaurantId;


}
