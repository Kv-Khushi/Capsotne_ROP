package com.orders.enums;


/**
 * Enumeration representing the various roles a user can have in the system.
 * <p>
 * The possible roles are:
 * <ul>
 *     <li>{@link #CUSTOMER} - A user who places orders and interacts with the restaurant.</li>
 *     <li>{@link #RESTAURANT_OWNER} - A user who owns or manages a restaurant and handles order management.</li>
 * </ul>
 * </p>
 */
public enum UserRole {

    /**
     * A user who places orders and interacts with the restaurant.
     */
    CUSTOMER,

    /**
     * A user who owns or manages a restaurant and handles order management.
     */
    RESTAURANT_OWNER
}

