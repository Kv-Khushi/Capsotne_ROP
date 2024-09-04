package com.restaurants.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotFoundException(final NotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        String errorMessage = String.join(",", errors);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage);
    }

}
