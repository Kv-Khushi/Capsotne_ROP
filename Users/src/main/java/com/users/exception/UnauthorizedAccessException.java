package com.users.exception;


/**
 * This exception is thrown when a user attempts to access a resource
 * they are not authorized to access.
 *
 * It extends {@link RuntimeException}, which means it is an unchecked exception.
 */
public class UnauthorizedAccessException extends RuntimeException{

    /**
     * Constructs a new UnauthorizedAccessException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public UnauthorizedAccessException(final String message) {
        super(message);
    }
}
