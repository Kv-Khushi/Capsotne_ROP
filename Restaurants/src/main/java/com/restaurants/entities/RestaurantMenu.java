package com.restaurants.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @NotBlank(message = "Item name cannot be blank")
    @Size(min = 2, max = 100, message = "Item name must be between 2 and 100 characters")
    private String itemName;

    /**
     * The price of the menu item.
     */
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be greater than 0")
    private Double price;

    /**
     * A description of the menu item.
     */

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    /**
     * Indicates whether the menu item is vegetarian or non-vegetarian.
     */
    @NotNull(message = "Please specify if the item is vegetarian or non-vegetarian")
    private Boolean vegNonVeg;

    /**
     * The unique identifier of the category to which the menu item belongs.
     */
    @NotNull(message = "Category ID is required")
    private Long categoryId;

    /**
     * The unique identifier of the restaurant that offers this menu item.
     */
    @NotNull(message = "Restaurant ID is required")
    private Long restaurantId;

    /**
     * The image of the menu item, stored as a byte array.
     */
    @Lob
    private byte[] imageUrl;

}
