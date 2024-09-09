package com.restaurants.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * Represents a restaurant entity in the system.
 * <p>
 * This class is designed to be immutable where possible. If the class is not designed
 * for extension, consider making it final. Methods that return mutable objects (e.g.,
 * arrays) are designed to return copies to avoid exposing internal state.
 * </p>
 */
@Data
@Entity
public class Restaurant {

    /**
     * The unique identifier for the restaurant.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;

    /**
     * The unique identifier of the user who owns or manages the restaurant.
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
     * The contact number for the restaurant.
     */
    private Long contactNumber;

    /**
     * A description of the restaurant.
     */
    private String restaurantDescription;

    /**
     * The opening hour of the restaurant.
     */
    private String openingHour;

    /**
     * The image of the restaurant, stored as a byte array.
     */
    @Lob
    private byte[] restaurantImage;

    /**
     * Returns a copy of the restaurant image byte array to prevent exposing internal representation.
     *
     * @return a copy of the restaurant image byte array, or null if the array is null.
     */
    public byte[] getRestaurantImage() {

        return restaurantImage != null ? restaurantImage.clone() : null;
    }

    /**
     * Sets a copy of the provided byte array to the restaurant image field to prevent external
     * modification of the internal state.
     *
     * @param restaurantImage a byte array representing the restaurant image, or null.
     */
    public void setRestaurantImage(final byte[] restaurantImage) {
        this.restaurantImage = restaurantImage != null ? restaurantImage.clone() : null;
    }
}
