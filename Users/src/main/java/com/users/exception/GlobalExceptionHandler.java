package com.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for handling various exceptions thrown by the application.
 * <p>
 * This class uses {@link RestControllerAdvice} to handle exceptions globally and return
 * appropriate HTTP responses with error details.
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Represents an error response containing the HTTP status and error message.
     */
    public static class ErrorResponse {
        private int status;
        private String message;

        /**
         * Constructs a new {@code ErrorResponse} with the specified status and message.
         *
         * @param status  the HTTP status code
         * @param message the error message
         */
        public ErrorResponse(int status, String message) {
            this.status = status;
            this.message = message;
        }

        // Getters and setters

        /**
         * Returns the HTTP status code.
         *
         * @return the HTTP status code
         */
        public int getStatus() {
            return status;
        }

        /**
         * Sets the HTTP status code.
         *
         * @param status the HTTP status code
         */
        public void setStatus(int status) {
            this.status = status;
        }

        /**
         * Returns the error message.
         *
         * @return the error message
         */
        public String getMessage() {
            return message;
        }

        /**
         * Sets the error message.
         *
         * @param message the error message
         */
        public void setMessage(String message) {
            this.message = message;
        }
    }

    /**
     * Handles {@link AlreadyExists} exceptions.
     * <p>
     * Returns an {@link ErrorResponse} with HTTP status code {@link HttpStatus#CONFLICT}
     * and the exception's message.
     * </p>
     *
     * @param ex the {@code AlreadyExists} exception
     * @return an {@link ErrorResponse} containing the error details
     */
    @ExceptionHandler(AlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse handleAlreadyExists(AlreadyExists ex) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    /**
     * Handles {@link NotFoundException} exceptions.
     * <p>
     * Returns an {@link ErrorResponse} with HTTP status code {@link HttpStatus#NOT_FOUND}
     * and the exception's message.
     * </p>
     *
     * @param ex the {@code NotFoundException} exception
     * @return an {@link ErrorResponse} containing the error details
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotFoundException(NotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    /**
     * Handles {@link MethodArgumentNotValidException} exceptions.
     * <p>
     * Returns an {@link ErrorResponse} with HTTP status code {@link HttpStatus#BAD_REQUEST}
     * and a message detailing the validation error.
     * </p>
     *
     * @param ex the {@code MethodArgumentNotValidException} exception
     * @return an {@link ErrorResponse} containing the error details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = "Validation failed: " + ex.getFieldError().getDefaultMessage();
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage);
    }
}
