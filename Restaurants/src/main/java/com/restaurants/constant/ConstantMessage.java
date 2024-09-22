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

    /**
     * Message indicating that a restaurant was added successfully.
     */
    public static final String RESTAURANT_ADD_SUCCESS = " Restaurant added successfully";

    /**
     * Message indicating that a food item was added successfully.
     */
    public static final String FOOD_ITEM_ADD_SUCCESS = " Food item added successfully";

    /**
     * Message indicating that a category was added successfully.
     */
    public static final String CATEGORY_ADD_SUCCESS = " Category added successfully";

    /**
     * Message indicating that the current user is not authorized to register a restaurant.
     */
    public static final String UNAUTHORIZED_USER = " Customers are not allowed to register a restaurant";

    /**
     * Message indicating that only JPG and PNG image formats are allowed.
     */
    public static final String INVALID_IMAGE_FORMAT = "Only JPG and PNG image formats are allowed.";

    /**
     * Message indicating that the category already exists.
     */
    public static final String CATEGORY_ALREADY_EXISTS = "Category already exists";

    /**
     * Message indicating that the food item already exists.
     */
    public static final String FOOD_ITEM_ALREADY_EXISTS = "Food Item already exists";

    /**
     * Message indicating that the restaurant already exists.
     */
    public static final String RESTAURANT_ALREADY_EXISTS="Restaurant already exists";

    /**
     * Message indicating that the user service is down.
     */
    public static final String USER_SERVICE_DOWN= "User Service Down";

    /**
     * Message indicating that image can not be empty or null.
     */
    public static final String INVALID_IMAGE = "Image cannot be empty or null";

}
