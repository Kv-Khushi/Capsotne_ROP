package com.restaurants.dto;

import lombok.Data;


import javax.validation.constraints.*;

/**
 * Data transfer object for creating or updating a restaurant.
 */
@Data
public class RestaurantRequest {

    /**
     * The ID of the user associated with the restaurant.
     */
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    /**
     * The name of the restaurant.
     * Must be between 1 and 100 characters in length.
     */
    @NotBlank(message = "Restaurant Name can not be blank")
    @Size(min = 5, max = MAX_RESTAURANT_NAME_LENGTH)
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Restaurant Name must not contain numeric values")
    private String restaurantName;

    /**
     * The address of the restaurant.
     * Must be between 1 and 255 characters in length.
     */
    @NotBlank(message = "Restaurant Address can not be blank")
    @Size(min = 5, max = MAX_RESTAURANT_ADDRESS_LENGTH)
    private String restaurantAddress;

    /**
     * The contact number of the restaurant.
     */
    @NotNull(message = "Contact Number cannot be null")
    @Positive(message = "Contact Number must be a positive number")
    @Digits(integer = 10, fraction = 0, message = "Contact Number must be a 10-digit number")
    @Min(value = 1000000000L, message = "Phone number should be exactly 10 digits")
    @Max(value = 9999999999L, message = "Phone number should be exactly 10 digits")
    @Pattern(regexp = "^[6789][0-9]{9}$", message = "Contact Number must start with 6, 7, 8, or 9 and be 10 digits long")
    private String contactNumber;

    /**
     * A description of the restaurant.
     */
    @NotBlank(message = "Restaurant Description can not be blank")
    private String restaurantDescription;

    /**
     * The opening hour of the restaurant.
     */
    private String openingHour;


    // Define constants for magic numbers
    private static final int MAX_RESTAURANT_NAME_LENGTH = 20;
    private static final int MAX_RESTAURANT_ADDRESS_LENGTH = 15;
}
