package com.restaurants.exception;

/**
 * Exception thrown when a requested resource is not found.
 */
public class ResourceNotFoundException extends RuntimeException{

    /**
     * Constructs a NotFoundException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method)
     */
    public ResourceNotFoundException(final String message) {

        super(message);
    }

}
