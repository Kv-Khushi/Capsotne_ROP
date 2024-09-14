package com.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

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
        /**
         * The HTTP status code.
         */
        private final int status;

        /**
         * The error message.
         */
        private final String message;

        /**
         * Constructs a new {@code ErrorResponse} with the specified status and message.
         *
         * @param status  the HTTP status code
         * @param message the error message
         */
        public ErrorResponse(final int status, final String message) {
            this.status = status;
            this.message = message;
        }

        /**
         * Returns the HTTP status code.
         *
         * @return the HTTP status code
         */
        public int getStatus() {
            return status;
        }

        /**
         * Returns the error message.
         *
         * @return the error message
         */
        public String getMessage() {
            return message;
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
    public ErrorResponse handleAlreadyExists(final AlreadyExists ex) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    /**
     * Handles {@link ResourceNotFoundException} exceptions.
     * <p>
     * Returns an {@link ErrorResponse} with HTTP status code {@link HttpStatus#NOT_FOUND}
     * and the exception's message.
     * </p>
     *
     * @param ex the {@code NotFoundException} exception
     * @return an {@link ErrorResponse} containing the error details
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotFoundException(final ResourceNotFoundException ex) {
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
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<List<ErrorResponse>> handleValidationExceptions(final Exception ex) {
      // Log exception details
        List<ErrorResponse> errors = new ArrayList<>();

        if (ex instanceof MethodArgumentNotValidException) {
            ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors().forEach(error -> {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();
                errors.add(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), String.format("Field '%s': %s", fieldName, errorMessage)));
            });
        } else if (ex instanceof BindException) {
            ((BindException) ex).getBindingResult().getFieldErrors().forEach(error -> {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();
                errors.add(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), String.format("Field '%s': %s", fieldName, errorMessage)));
            });
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }


}
