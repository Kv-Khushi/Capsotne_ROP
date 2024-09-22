package com.restaurants.exception;

/**
 * Exception thrown when a resource already exists.
 */
public class AlreadyExistsException extends RuntimeException{


    /**
     * Default constructor for AlreadyExistsException.
     */
    public AlreadyExistsException(){
        // Default constructor
    }


    /**
     * Constructs an AlreadyExistsException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public AlreadyExistsException(final String message){
        super(message);
    }
}
