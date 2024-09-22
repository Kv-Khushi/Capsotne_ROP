package com.orders.exception;


/**
 * Exception thrown when a requested resource is not found.
 */
public class ResourceNotFoundException extends RuntimeException {


    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * @param message the detail message of the exception
     */
    public ResourceNotFoundException(final String message) {
        super(message);
    }
}
