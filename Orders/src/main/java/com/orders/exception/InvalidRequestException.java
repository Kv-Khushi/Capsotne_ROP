package com.orders.exception;

/**
 * Exception thrown when an invalid request is made.
 */

public class InvalidRequestException extends RuntimeException {

    /**
     * Constructs a new InvalidRequestException with the specified detail message.
     *
     * @param message the detail message of the exception
     */
    public InvalidRequestException(String message) {
        super(message);
    }
}
