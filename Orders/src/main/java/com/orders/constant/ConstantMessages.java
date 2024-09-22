package com.orders.constant;



/**
 * This class defines constant string messages used throughout the application
 * for error messages, success messages, and validation messages.
 * These constants help maintain consistency in messaging and avoid hardcoded
 * strings in different parts of the application.
 */
public class ConstantMessages {
    /**
     * Message indicating that the provided user ID is invalid.
     */
    public static final String INVALID_USER_ID = "Invalid user ID.";
    /**
     * Message indicating that the provided restaurant ID is invalid.
     */
    public static final String INVALID_RESTAURANT_ID = "Invalid restaurant ID.";
    /**
     * Message indicating that the provided food item ID is invalid.
     */
    public static final String INVALID_FOOD_ITEM_ID = "Invalid food item ID.";
    /**
     * Error message when items from multiple restaurants are being added to the cart.
     */
    public static final String MULTIPLE_RESTAURANT_ERROR = "You can only add items from one restaurant at a time.";

    /**
     * Message indicating that the requested item was not found in the cart.
     */
    public static final String ITEM_NOT_FOUND = "Item not found in cart.";

    /**
     * Error message when a user tries to set a negative quantity for an item.
     */
    public static final String NEGATIVE_QUANTITY_ERROR = "Quantity cannot be negative.";

    /**
     * Message indicating that the requested resource was not found.
     */
    public static final String RESOURCE_NOT_FOUND = "The requested resource was not found.";

    /**
     * Message indicating that the request is invalid.
     */
    public static final String INVALID_REQUEST = "The request provided is invalid.";


    /**
     * Success message when an item is removed from the cart.
     */
    public static final String ITEM_REMOVED_SUCCESSFULLY = "Item removed from cart successfully.";

    /**
     * Success message when the quantity of an item in the cart is updated.
     */
    public static final String ITEM_QUANTITY_UPDATED_SUCCESSFULLY = "Item quantity updated successfully.";

    /**
     * Success message when an order is canceled.
     */
    public static final String ORDER_CANCELED_SUCCESSFULLY = "Order canceled successfully.";

    /**
     * Error message when an order cannot be canceled.
     */
    public static final String ORDER_CANNOT_BE_CANCELED = "Order cannot be canceled.";

    /**
     * Message indicating that there are no items in the user's cart.
     */
    public static final String NO_ITEMS_IN_CART = "No items found in the cart.";
    /**
     * Message indicating that the provided address ID is invalid for the user.
     */
    public static final String INVALID_ADDRESS_ID = "Invalid address ID for this user.";

    /**
     * Message indicating that restaurant owners are not allowed to add items to the cart.
     */
    public static final String RESTAURANT_OWNER_CANNOT_ADD_TO_CART = "Restaurant owners cannot add items to the cart.";

    /**
     * Message indicating that no orders were found for the user.
     */
    public static final String NO_ORDERS_FOUND = "No orders found for the user.";

    /**
     * Message indicating that insufficient amount in wallet.
     */
    public static final String INSUFFICIENT_AMOUNT= "Insufficient amount in wallet";

    /**
     * Message indicating that order completed successfully.
     */
    public static final String ORDER_COMPLETED_SUCCESSFULLY = "Order completed successfully.";

    /**
     * Message indicating that user service is down.
     */
    public static final String USER_SERVICE_DOWN= "User Service Down";

    /**
     * Message indicating that restaurant service is down.
     */
    public static final String RESTAURANT_SERVICE_DOWN="Restaurant Service Down";
}


