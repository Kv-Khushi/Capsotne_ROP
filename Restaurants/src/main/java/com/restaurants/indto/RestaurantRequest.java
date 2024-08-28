package com.restaurants.indto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RestaurantRequest {

    @NotNull
    private Long userId;

    @NotNull
    @Size(min = 1, max = 100)
    private String restaurantName;

    @NotNull
    @Size(min = 1, max = 255)
    private String restaurantAddress;

    @NotNull
    private Long contactNumber;

    private String restaurantDescription;

    private Long openingHour;

    private String restaurantImages;
}
