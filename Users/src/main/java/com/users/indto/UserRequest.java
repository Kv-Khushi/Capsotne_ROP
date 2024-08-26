    package com.users.indto;

    import com.users.enums.UserRole;
    import lombok.Data;

    import javax.validation.constraints.*;

    @Data
    public class UserRequest {

        private Long userId;

        @NotNull(message = "phone number cannot be null")
        @Digits(integer = 10, fraction = 0, message = "phone number should be exactly 10 digits")
        @Min(value = 1000000000L, message = "phone number should be exactly 10 digits")
        @Max(value = 9999999999L, message = "phone number should be exactly 10 digits")
        private Long phoneNumber;


        // username should not be null or empty
        // username should have at least 2 characters
        @NotNull
        @Size(min = 2,message = "user name should have at least 2 characters")
        private String userName;

        @NotBlank(message = "Email must not be blank")
        @Email
        @Pattern(
                regexp = "^[A-Za-z0-9._%+-]+@nucleusteq\\.com$",
                message = "email must end with nucleusteq.com"
        )
        private String userEmail;

        // password should not be null or empty
        // password should have at least 8 characters
        @NotBlank(message = "password can not be empty")
        @Size(min = 8, message = "password should have at least 8 characters")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$",
                message = "password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character (@#$%^&+=)"
        )
        private String userPassword;

        private UserRole userRole;

    }
