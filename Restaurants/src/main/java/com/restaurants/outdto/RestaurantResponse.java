package com.restaurants.outdto;

import lombok.Data;

@Data
public class RestaurantResponse{

    private Long userId;

    private String restaurantName;

    private String restaurantAddress;

    private Long contactNumber;

    private String restaurantDescription;

    private Long openingHour;

    private String restaurantImages;

}

