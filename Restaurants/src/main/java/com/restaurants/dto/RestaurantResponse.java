package com.restaurants.dto;

import lombok.Data;

import javax.persistence.Lob;

/**
 * Data transfer object representing the response for a restaurant.
 */
@Data
public class RestaurantResponse {
private Long restaurantId;
    /**
     * The unique identifier of the user who owns the restaurant.
     */
    private Long userId;

    /**
     * The name of the restaurant.
     */
    private String restaurantName;

    /**
     * The address of the restaurant.
     */
    private String restaurantAddress;

    /**
     * The contact number of the restaurant.
     */
    private String contactNumber;

    /**
     * A description of the restaurant.
     */
    private String restaurantDescription;

    /**
     * The opening hour of the restaurant.
     */
    private String openingHour;

    /**
     * The image of the restaurant.
     */
    @Lob
    private byte[] restaurantImage;

    /**
     * Returns a copy of the restaurant image byte array to prevent exposing internal representation.
     *
     * @return a copy of the restaurant image byte array, or null if the array is null
     */
    public byte[] getRestaurantImage() {
        return restaurantImage != null ? restaurantImage.clone() : null;
    }

    /**
     * Sets a copy of the provided byte array to the restaurant image field to prevent external modification
     * of the internal state.
     *
     * @param restaurantImage a byte array representing the restaurant image, or null
     */
    public void setRestaurantImage(final byte[] restaurantImage) {
        this.restaurantImage = restaurantImage != null ? restaurantImage.clone() : null;
    }
}
