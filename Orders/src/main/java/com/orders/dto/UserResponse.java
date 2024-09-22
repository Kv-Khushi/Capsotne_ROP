package com.orders.dto;

import com.orders.enums.UserRole;
import lombok.Data;


/**
 * Data Transfer Object (DTO) representing the response for a user.
 * This class contains user-related information such as user ID, username, role, and wallet balance.
 * <p>
 * Lombok's {@code @Data} annotation is used to automatically generate
 * getters, setters, equals, hashCode, and toString methods.
 * </p>
 */
@Data
public class UserResponse {
    /**
     * The unique ID of the user.
     */
    private Long userId;

    /**
     * The name of the user.
     */
    private String userName;

    /**
     * The role of the user (e.g., ADMIN, CUSTOMER, etc.).
     * This is represented by the {@link UserRole} enum.
     */
    private UserRole userRole;

    /**
     * The wallet balance of the user.
     */
    private Double wallet;

}
