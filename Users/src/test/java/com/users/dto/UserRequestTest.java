//package com.users.dto;
//
//import com.users.enums.UserRole;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.Validation;
//import javax.validation.Validator;
//import javax.validation.ValidatorFactory;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class UserRequestTest {
//
//    private Validator validator;
//
//    @BeforeEach
//    public void setUp() {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        validator = factory.getValidator();
//    }
//
//    @Test
//    public void testValidUserRequest() {
//        UserRequest userRequest = buildValidUserRequest();
//
//        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
//        assertTrue(violations.isEmpty());
//    }
//
//    @Test
//    public void testNullPhoneNumber() {
//        UserRequest userRequest = buildValidUserRequest();
//        userRequest.setPhoneNumber(null);
//
//        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
//        assertFalse(violations.isEmpty());
//        assertEquals("Phone number cannot be null", violations.iterator().next().getMessage());
//    }
//
//    @Test
//    public void testInvalidPhoneNumberTooShort() {
//        UserRequest userRequest = buildValidUserRequest();
//        userRequest.setPhoneNumber(999999999L); // 9 digits
//
//        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
//        assertFalse(violations.isEmpty());
//        assertEquals("Phone number should be exactly 10 digits", violations.iterator().next().getMessage());
//    }
//
//    @Test
//    public void testInvalidPhoneNumberTooLong() {
//        UserRequest userRequest = buildValidUserRequest();
//        userRequest.setPhoneNumber(10000000000L); // 11 digits
//
//        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
//        assertFalse(violations.isEmpty());
//        assertEquals("Phone number should be exactly 10 digits", violations.iterator().next().getMessage());
//    }
//
//    @Test
//    public void testBlankUserName() {
//        UserRequest userRequest = buildValidUserRequest();
//        userRequest.setUserName(null);
//
//        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
//        assertFalse(violations.isEmpty());
//        assertEquals("Username cannot be null", violations.iterator().next().getMessage());
//    }
//
//    @Test
//    public void testShortUserName() {
//        UserRequest userRequest = buildValidUserRequest();
//        userRequest.setUserName("A"); // 1 character
//
//        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
//        assertFalse(violations.isEmpty());
//        assertEquals("Username should have at least 2 characters", violations.iterator().next().getMessage());
//    }
//
//    @Test
//    public void testBlankEmail() {
//        UserRequest userRequest = buildValidUserRequest();
//        userRequest.setUserEmail("");
//
//        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
//        assertFalse(violations.isEmpty());
//        assertEquals("Email must not be blank", violations.iterator().next().getMessage());
//    }
//
//    @Test
//    public void testInvalidEmailFormat() {
//        UserRequest userRequest = buildValidUserRequest();
//        userRequest.setUserEmail("invalidemail.com");
//
//        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
//        assertFalse(violations.isEmpty());
//        assertEquals("Email must end with nucleusteq.com", violations.iterator().next().getMessage());
//    }
//
//    @Test
//    public void testInvalidEmailDomain() {
//        UserRequest userRequest = buildValidUserRequest();
//        userRequest.setUserEmail("john.doe@gmail.com");
//
//        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
//        assertFalse(violations.isEmpty());
//        assertEquals("Email must end with nucleusteq.com", violations.iterator().next().getMessage());
//    }
//
//    @Test
//    public void testBlankPassword() {
//        UserRequest userRequest = buildValidUserRequest();
//        userRequest.setUserPassword("");
//
//        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
//        assertFalse(violations.isEmpty());
//        assertEquals("Password cannot be empty", violations.iterator().next().getMessage());
//    }
//
//    @Test
//    public void testShortPassword() {
//        UserRequest userRequest = buildValidUserRequest();
//        userRequest.setUserPassword("Pass1!");
//
//        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
//        assertFalse(violations.isEmpty());
//        assertEquals("Password should have at least 8 characters", violations.iterator().next().getMessage());
//    }
//
//    @Test
//    public void testWeakPassword() {
//        UserRequest userRequest = buildValidUserRequest();
//        userRequest.setUserPassword("password"); // No digits, uppercase, or special characters
//
//        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
//        assertFalse(violations.isEmpty());
//        assertEquals("Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character (@#$%^&+=)",
//                violations.iterator().next().getMessage());
//    }
//
//    @Test
//    public void testValidPassword() {
//        UserRequest userRequest = buildValidUserRequest();
//        userRequest.setUserPassword("StrongPass1@");
//
//        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
//        assertTrue(violations.isEmpty());
//    }
//
//    @Test
//    public void testUserRole() {
//        UserRequest userRequest = buildValidUserRequest();
//        userRequest.setUserRole(UserRole.RESTAURANT_OWNER);
//
//        assertEquals(UserRole.RESTAURANT_OWNER, userRequest.getUserRole());
//    }
//
//    private UserRequest buildValidUserRequest() {
//        UserRequest userRequest = new UserRequest();
//        userRequest.setPhoneNumber(1234567890L);
//        userRequest.setUserName("John Doe");
//        userRequest.setUserEmail("john.doe@nucleusteq.com");
//        userRequest.setUserPassword("Password1@");
//        userRequest.setUserRole(UserRole.CUSTOMER);
//        return userRequest;
//    }
//}




package com.users.dto;

import com.users.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidUserRequest() {
        UserRequest userRequest = buildValidUserRequest();

        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testNullPhoneNumber() {
        UserRequest userRequest = buildValidUserRequest();
        userRequest.setPhoneNumber(null);

        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        assertFalse(violations.isEmpty());
        assertEquals("Phone number cannot be null", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidPhoneNumberTooShort() {
        UserRequest userRequest = buildValidUserRequest();
        userRequest.setPhoneNumber(999999999L); // 9 digits

        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        assertFalse(violations.isEmpty());
        assertEquals("Phone number should be exactly 10 digits", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidPhoneNumberTooLong() {
        UserRequest userRequest = buildValidUserRequest();
        userRequest.setPhoneNumber(10000000000L); // 11 digits

        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        assertFalse(violations.isEmpty());
        assertEquals("Phone number should be exactly 10 digits", violations.iterator().next().getMessage());
    }

    @Test
    public void testBlankUserName() {
        UserRequest userRequest = buildValidUserRequest();
        userRequest.setUserName(null);

        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        assertFalse(violations.isEmpty());
        assertEquals("Username cannot be null", violations.iterator().next().getMessage());
    }

    @Test
    public void testShortUserName() {
        UserRequest userRequest = buildValidUserRequest();
        userRequest.setUserName("A"); // 1 character

        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        assertFalse(violations.isEmpty());
        assertEquals("Username should have at least 2 characters", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidEmailDomain() {
        UserRequest userRequest = buildValidUserRequest();
        userRequest.setUserEmail("john.doe@gmail.com");

        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        assertFalse(violations.isEmpty());
        assertEquals("Email must end with nucleusteq.com", violations.iterator().next().getMessage());
    }

    @Test
    public void testWeakPassword() {
        UserRequest userRequest = buildValidUserRequest();
        userRequest.setUserPassword("password"); // No digits, uppercase, or special characters

        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        assertFalse(violations.isEmpty());
        assertEquals("Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character (@#$%^&+=)",
                violations.iterator().next().getMessage());
    }

    @Test
    public void testValidPassword() {
        UserRequest userRequest = buildValidUserRequest();
        userRequest.setUserPassword("StrongPass1@");

        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testUserRole() {
        UserRequest userRequest = buildValidUserRequest();
        userRequest.setUserRole(UserRole.RESTAURANT_OWNER);

        assertEquals(UserRole.RESTAURANT_OWNER, userRequest.getUserRole());
    }

    private UserRequest buildValidUserRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setPhoneNumber(1234567890L);
        userRequest.setUserName("John Doe");
        userRequest.setUserEmail("john.doe@nucleusteq.com");
        userRequest.setUserPassword("Password1@");
        userRequest.setUserRole(UserRole.CUSTOMER);
        return userRequest;
    }
}
