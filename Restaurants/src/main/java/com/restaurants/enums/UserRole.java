package com.restaurants.enums;


/**
 * Enumeration representing the different roles a user can have within the restaurant management system.
 * <p>
 * The possible roles are:
 * <ul>
 *     <li>{@link #CUSTOMER} - Represents a user who interacts with the system as a customer, placing orders and managing their own account.</li>
 *     <li>{@link #RESTAURANT_OWNER} - Represents a user who owns or manages a restaurant.</li>
 * </ul>
 * </p>
 */
public enum UserRole {
    /**
     * Represents a user who interacts with the system as a customer, placing orders and managing their own account.
     */
    CUSTOMER,

    /**
     * Represents a user who owns or manages a restaurant and is responsible for managing restaurant operations,
     * including adding and managing menu items.
     */
    RESTAURANT_OWNER
}
