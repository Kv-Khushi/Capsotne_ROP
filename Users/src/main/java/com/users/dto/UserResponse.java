package com.users.dto;

import com.users.enums.UserRole;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for user responses.
 * <p>
 * This class is used to encapsulate the data returned in response to user-related requests.
 * It includes information such as user ID, phone number, username, email, password, and wallet balance.
 * </p>
 */
@Data
public class UserResponse {

    /**
     * The unique identifier of the user.
     */
    private Long userId;

    /**
     * The phone number of the user.
     */
    private Long phoneNumber;

    /**
     * The username of the user.
     */
    private String userName;

    /**
     * The email address of the user.
     */
    private String userEmail;

    /**
     * The password of the user.
     * <p>
     * Note: In a real application, it is not advisable to include passwords in responses for security reasons.
     * </p>
     */
    private String userPassword;

    /**
     * The wallet balance of the user.
     */
    private Double wallet;

    /**
     * The role of the user.
     */

    private UserRole userRole;
}
