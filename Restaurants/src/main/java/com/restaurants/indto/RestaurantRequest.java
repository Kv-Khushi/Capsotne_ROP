package com.restaurants.indto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Data transfer object for creating or updating a restaurant.
 */
@Data
public class RestaurantRequest {

    /**
     * The ID of the user associated with the restaurant.
     */
    @NotNull
    private Long userId;

    /**
     * The name of the restaurant.
     * Must be between 1 and 100 characters in length.
     */
    @NotNull
    @Size(min = 1, max = MAX_RESTAURANT_NAME_LENGTH)
    private String restaurantName;

    /**
     * The address of the restaurant.
     * Must be between 1 and 255 characters in length.
     */
    @NotNull
    @Size(min = 1, max = MAX_RESTAURANT_ADDRESS_LENGTH)
    private String restaurantAddress;

    /**
     * The contact number of the restaurant.
     */
//    @NotNull
    private Long contactNumber;

    /**
     * A description of the restaurant.
     */
    private String restaurantDescription;

    /**
     * The opening hour of the restaurant.
     */
    private String openingHour;

    private MultipartFile restaurantImage;

    // Define constants for magic numbers
    private static final int MAX_RESTAURANT_NAME_LENGTH = 100;
    private static final int MAX_RESTAURANT_ADDRESS_LENGTH = 255;
}
