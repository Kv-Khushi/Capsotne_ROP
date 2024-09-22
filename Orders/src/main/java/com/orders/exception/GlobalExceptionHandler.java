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
    public ResponseEntity<MessageResponse> handleResourceNotFoundException(final ResourceNotFoundException ex,
                                                                           final WebRequest request) {
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
    public ResponseEntity<MessageResponse> handleInvalidRequestException(final InvalidRequestException ex,
                                                                         final WebRequest request) {
        MessageResponse messageResponse = new MessageResponse(ex.getMessage());
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }
}
