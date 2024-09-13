package com.restaurants.dto;

import lombok.Data;

import javax.persistence.Lob;

/**
 * Data transfer object representing the response for a restaurant menu item.
 */
@Data
public class RestaurantMenuResponse {

    /**
     * The unique identifier of the menu item.
     */
    private Long itemId;

    /**
     * The name of the menu item.
     */
    private String itemName;

    /**
     * The price of the menu item.
     */
    private Double price;

    /**
     * A description of the menu item.
     */
    private String description;

    /**
     * Indicates whether the menu item is vegetarian or non-vegetarian.
     */
    private Boolean vegNonVeg;

    /**
     * The unique identifier of the category to which the menu item belongs.
     */
    private Long categoryId;

    /**
     * The unique identifier of the restaurant that offers the menu item.
     */
    private Long restaurantId;

    /**
     * The image URL of the menu item.
     */
    @Lob
    private byte[] imageUrl;

    /**
     * Returns a copy of the image URL byte array to prevent exposing internal representation.
     *
     * @return a copy of the image URL byte array, or null if the array is null
     */
    public byte[] getImageUrl() {
        return imageUrl != null ? imageUrl.clone() : null;
    }

    /**
     * Sets a copy of the provided byte array to the image URL field to prevent external modification
     * of the internal state.
     *
     * @param imageUrl a byte array representing the image URL, or null
     */
    public void setImageUrl(final byte[] imageUrl) {
        this.imageUrl = imageUrl != null ? imageUrl.clone() : null;
    }
}
