package com.restaurants.exception;


/**
 * Exception thrown when a user is unauthorized to perform an action.
 */
public class UnauthorizedException extends RuntimeException {
    /**
     * Constructs a new UnauthorizedException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public UnauthorizedException(final String message) {
        super(message);
    }
}
