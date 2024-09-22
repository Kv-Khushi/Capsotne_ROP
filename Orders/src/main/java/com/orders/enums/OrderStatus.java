package com.orders.enums;


/**
 * Enumeration representing the various statuses an order can have.
 * <p>
 * The possible statuses are:
 * <ul>
 *
 *     <li>{@link #COMPLETED} - The order has been fulfilled and completed.</li>
 *     <li>{@link #CANCELED} - The order has been canceled and will not be processed.</li>
 *     <li>{@link #PENDING} - The order is pending and awaiting further action.</li>
 * </ul>
 * </p>
 */
public enum OrderStatus {
    /**
     * The order has been fulfilled and completed.
     */
    COMPLETED,

    /**
     * The order has been canceled and will not be processed.
     */
    CANCELED,

    /**
     * The order is pending and awaiting further action.
     */
    PENDING
}
