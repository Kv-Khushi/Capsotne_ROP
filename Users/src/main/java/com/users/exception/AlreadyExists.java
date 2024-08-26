package com.users.exception;

/**
 * Exception thrown when a user tries to create or perform an operation that
 * violates a uniqueness constraint, such as attempting to register an email
 * that already exists in the system.
 * <p>
 * This is a custom runtime exception that extends {@link RuntimeException}.
 * </p>
 */
public class AlreadyExists extends RuntimeException {

 /**
  * Constructs a new {@code AlreadyExists} exception with the specified detail message.
  *
  * @param message the detail message, which provides more information about the cause of the exception
  */
 public AlreadyExists(String message) {
  super(message);
 }
}
