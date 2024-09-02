package com.restaurants.constant;

/**
 * This class holds constant messages used throughout the application.
 * It cannot be instantiated or subclassed.
 */
public final class ConstantMessage {

    // Private constructor to prevent instantiation
    private ConstantMessage() {
    }

    /**
     * Message indicating that a restaurant was not found.
     */
    public static final String RESTAURANT_NOT_FOUND = "Restaurant not found";

    /**
     * Message indicating that a category was not found.
     */
    public static final String CATEGORY_NOT_FOUND = "Category not found";

    /**
     * Message indicating that a food item was not found.
     */
    public static final String FOOD_ITEM_NOT_FOUND = "FoodItem not found";
}
