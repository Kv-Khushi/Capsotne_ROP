package com.users.exception;
/**
 * Exception thrown when an invalid request is made to the server.
 */
public class InvalidRequestException extends RuntimeException {
    /**
     * Constructs a new InvalidRequestException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public InvalidRequestException(final String message) {
        super(message);
    }
}
