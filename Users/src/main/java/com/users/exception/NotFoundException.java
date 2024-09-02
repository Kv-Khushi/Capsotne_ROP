package com.users.exception;

/**
 * Exception thrown when a requested resource is not found.
 * <p>
 * This is a custom runtime exception that extends {@link RuntimeException}.
 * It is typically used to indicate that a resource, such as a user or an address,
 * could not be found in the system.
 * </p>
 */
public class NotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code NotFoundException} with the specified detail message.
     *
     * @param message the detail message, which provides more information about the cause of the exception
     */
    public NotFoundException(final String message) {
        super(message);
    }
}
