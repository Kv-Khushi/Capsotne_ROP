package com.orders.exception;

import com.orders.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ResourceNotFoundException and returns a NOT_FOUND response.
     *
     * @param ex the exception thrown
     * @param request the web request object
     * @return ResponseEntity containing MessageResponse with NOT_FOUND status
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MessageResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        MessageResponse messageResponse = new MessageResponse(ex.getMessage());
        return new ResponseEntity<>(messageResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles InvalidRequestException and returns a BAD_REQUEST response.
     *
     * @param ex the exception thrown
     * @param request the web request object
     * @return ResponseEntity containing MessageResponse with BAD_REQUEST status
     */
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<MessageResponse> handleInvalidRequestException(InvalidRequestException ex, WebRequest request) {
        MessageResponse messageResponse = new MessageResponse(ex.getMessage());
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other exceptions and returns an INTERNAL_SERVER_ERROR response.
     *
     * @param ex the exception thrown
     * @param request the web request object
     * @return ResponseEntity containing MessageResponse with INTERNAL_SERVER_ERROR status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponse> handleGlobalException(Exception ex, WebRequest request) {
        MessageResponse messageResponse = new MessageResponse("An unexpected error occurred.");
        return new ResponseEntity<>(messageResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
