package com.users.indto;

import com.users.enums.UserRole;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * Data Transfer Object (DTO) for user requests.
 * <p>
 * This class is used to encapsulate the data needed to create or update a user in the system.
 * It includes information such as user ID, phone number, username, email, password, and user role.
 * Validation constraints are applied to ensure the data meets the required format and rules.
 * </p>
 */
@Data
public class UserRequest {

    /**
     * The unique identifier for the user. This field is optional.
     */
    private Long userId;

    /**
     * The phone number of the user.
     * <p>
     * This field cannot be null and must be exactly 10 digits long.
     * The value must be between 1000000000 and 9999999999 inclusive.
     * </p>
     */
    @NotNull(message = "Phone number cannot be null")
    @Digits(integer = 10, fraction = 0, message = "Phone number should be exactly 10 digits")
    @Min(value = 1000000000L, message = "Phone number should be exactly 10 digits")
    @Max(value = 9999999999L, message = "Phone number should be exactly 10 digits")
    private Long phoneNumber;

    /**
     * The username of the user.
     * <p>
     * This field cannot be null or empty and must have at least 2 characters.
     * </p>
     */
    @NotNull(message = "Username cannot be null")
    @Size(min = 2, message = "Username should have at least 2 characters")
    private String userName;

    /**
     * The email address of the user.
     * <p>
     * This field cannot be blank and must be a valid email address.
     * The email must end with {@code nucleusteq.com}.
     * </p>
     */
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@nucleusteq\\.com$",
            message = "Email must end with nucleusteq.com"
    )
    private String userEmail;

    /**
     * The password of the user.
     * <p>
     * This field cannot be empty and must be at least 8 characters long.
     * The password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character (@#$%^&+=).
     * </p>
     */
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password should have at least 8 characters")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character (@#$%^&+=)"
    )
    private String userPassword;

    /**
     * The role of the user in the system.
     * <p>
     * This field specifies the user's role and can be one of the predefined roles in the {@link UserRole} enum.
     * </p>
     */
    private UserRole userRole;
}
