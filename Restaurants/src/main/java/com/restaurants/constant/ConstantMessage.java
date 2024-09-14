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


    /**
     * Message indicating that a user was not found.
     */
    public static final String USER_NOT_FOUND = "User not found";

    public static final String RESTAURANT_ADD_SUCCESS = " Restaurant added successfully";
    public static final String FOOD_ITEM_ADD_SUCCESS = " Food item added successfully";
    public static final String CATEGORY_ADD_SUCCESS = " Category added successfully";

    public static final String UNAUTHORIZED_USER = " Customers are not allowed to register a restaurant";


    public static final String INVALID_IMAGE_FORMAT = "Only JPG and PNG image formats are allowed.";

    public static final String CATEGORY_ALREADY_EXISTS = "Category already exists";

    public static final String FOOD_ITEM_ALREADY_EXISTS = "Food Item already exists";

}
