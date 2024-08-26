package com.users.indto;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * Data Transfer Object (DTO) for login requests.
 * <p>
 * This class is used to encapsulate the data needed for a user login request.
 * It includes the user's email and password, which are required for authentication.
 * </p>
 */
@Data
public class LoginRequest {

    /**
     * The email address of the user trying to log in.
     */
    @NotNull(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@nucleusteq\\.com$",
            message = "Email must end with nucleusteq.com"
    )
    private String userEmail;

    /**
     * The password of the user trying to log in.
     */
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password should have at least 8 characters")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character (@#$%^&+=)"
    )
    private String userPassword;

    /**
     * Returns a string representation of the {@code LoginRequest} object.
     * <p>
     * The returned string includes the email and password fields, formatted for easy readability.
     * Note: For security reasons, avoid logging sensitive information like passwords in a real application.
     * </p>
     *
     * @return a string representation of the {@code LoginRequest} object
     */
    @Override
    public String toString() {
        return "LoginRequest{"
                + "userEmail='" + userEmail + '\''
                + ", userPassword='" + userPassword + '\''
                + '}';
    }
}
