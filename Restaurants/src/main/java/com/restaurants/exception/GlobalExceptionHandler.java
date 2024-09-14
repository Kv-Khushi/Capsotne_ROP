package com.restaurants.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
 * Global exception handler for the application to handle various exceptions.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    /**
     * Represents an error response containing status and message.
     */
    public static class ErrorResponse {
        private int status;
        private String message;

        /**
         * Constructs an ErrorResponse with the given status and message.
         *
         * @param status  the HTTP status code
         * @param message the error message
         */
        public ErrorResponse(final int status, final String message) {
            this.status = status;
            this.message = message;
        }

        /**
         * Gets the HTTP status code of the error response.
         *
         * @return the HTTP status code
         */
        public int getStatus() {
            return status;
        }

        /**
         * Sets the HTTP status code of the error response.
         *
         * @param status the HTTP status code
         */
        public void setStatus(final int status) {
            this.status = status;
        }

        /**
         * Gets the error message of the error response.
         *
         * @return the error message
         */
        public String getMessage() {
            return message;
        }

        /**
         * Sets the error message of the error response.
         *
         * @param message the error message
         */
        public void setMessage(final String message) {
            this.message = message;
        }
    }

    /**
     * Handles NotFoundException and returns an error response with status 404.
     *
     * @param ex the NotFoundException to handle
     * @return an ErrorResponse with status 404 and exception message
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotFoundException(final ResourceNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleUnauthorizedUserException(final UnauthorizedException ex) {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }



    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestException(InvalidRequestException ex) {
        logger.error("Handling InvalidRequestException: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<List<ErrorResponse>> handleValidationExceptions(Exception ex) {
        logger.info("Handling validation exceptions: {}", ex.getMessage()); // Log exception details
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

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyExistsException(AlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

}
