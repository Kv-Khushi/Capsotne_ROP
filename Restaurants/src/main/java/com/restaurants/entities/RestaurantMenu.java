package com.restaurants.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Represents a menu item in a restaurant.
 * <p>
 * This class is designed to be immutable where possible. If the class is not designed
 * for extension, consider making it final. Methods that return mutable objects (e.g.,
 * arrays) are designed to return copies to avoid exposing internal state.
 * </p>
 */
@Data
@NoArgsConstructor
@Entity
public class RestaurantMenu {

    /**
     * The unique identifier for the menu item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
     * The unique identifier of the restaurant that offers this menu item.
     */
    private Long restaurantId;

    /**
     * The image of the menu item, stored as a byte array.
     */
    @Lob
    private byte[] imageUrl;

    /**
     * Returns a copy of the imageUrl byte array to prevent exposing internal representation.
     *
     * @return a copy of the imageUrl byte array, or null if the array is null.
     */
    public byte[] getImageUrl() {
        return imageUrl != null ? imageUrl.clone() : null;
    }

    /**
     * Sets a copy of the provided byte array to the imageUrl field to prevent external
     * modification of the internal state.
     *
     * @param imageUrl a byte array representing the image of the menu item, or null.
     */
    public void setImageUrl(final byte[] imageUrl) {

        this.imageUrl = imageUrl != null ? imageUrl.clone() : null;
    }
}
