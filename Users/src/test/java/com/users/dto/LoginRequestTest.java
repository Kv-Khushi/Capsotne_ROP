package com.users.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LoginRequestTest {

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    @Test
    public void testGettersAndSetters() {
        LoginRequest loginRequest = new LoginRequest();

        // Test userEmail
        assertNull(loginRequest.getUserEmail());
        String email = "test@nucleusteq.com";
        loginRequest.setUserEmail(email);
        assertEquals(email, loginRequest.getUserEmail());

        // Test userPassword
        assertNull(loginRequest.getUserPassword());
        String password = "Password1@";
        loginRequest.setUserPassword(password);
        assertEquals(password, loginRequest.getUserPassword());
    }

    @Test
    public void testToString() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("test@nucleusteq.com");
        loginRequest.setUserPassword("Password1@");

        String expectedString = "LoginRequest{userEmail='test@nucleusteq.com', userPassword='Password1@'}";
        assertEquals(expectedString, loginRequest.toString());
    }

    @Test
    public void testValidation() {
        // Create a LoginRequest object with invalid data
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("invalid-email");
        loginRequest.setUserPassword("short");

        // Validate the LoginRequest
        Set<javax.validation.ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        // Check for validation errors
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("userEmail")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("userPassword")));
    }

    @Test
    public void testValidLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("test@nucleusteq.com");
        loginRequest.setUserPassword("Password1@");

        // Validate the LoginRequest
        Set<javax.validation.ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        // Check that there are no validation errors
        assertTrue(violations.isEmpty());
    }
}
