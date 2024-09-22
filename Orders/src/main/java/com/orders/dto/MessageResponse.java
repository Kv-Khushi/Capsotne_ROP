package com.orders.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for sending a simple message response.
 * This class contains a single field for a message string, which can be used
 * to convey success, error, or informational messages in the application.
 * <p>
 * Lombok's {@code @Data} annotation is used to automatically generate getters,
 * setters, equals, hashCode, and toString methods.
 * </p>
 * <p>
 * The {@code @AllArgsConstructor} annotation is used to automatically generate a constructor
 * with all class fields as parameters.
 * </p>
 */
@Data
@AllArgsConstructor
public class MessageResponse {
    private String message;
}
