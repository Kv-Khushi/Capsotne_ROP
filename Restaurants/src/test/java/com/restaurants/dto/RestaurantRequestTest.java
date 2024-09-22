package com.restaurants.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidRestaurantRequest() {
        RestaurantRequest restaurantRequest = buildValidRestaurantRequest();

        Set<ConstraintViolation<RestaurantRequest>> violations = validator.validate(restaurantRequest);

    }

    @Test
    public void testNullUserId() {
        RestaurantRequest restaurantRequest = buildValidRestaurantRequest();
        restaurantRequest.setUserId(null);

        Set<ConstraintViolation<RestaurantRequest>> violations = validator.validate(restaurantRequest);
        assertFalse(violations.isEmpty());

    }

    @Test
    public void testBlankRestaurantName() {
        RestaurantRequest restaurantRequest = buildValidRestaurantRequest();
        restaurantRequest.setRestaurantName("");

        Set<ConstraintViolation<RestaurantRequest>> violations = validator.validate(restaurantRequest);
        assertFalse(violations.isEmpty());

    }

    @Test
    public void testShortRestaurantName() {
        RestaurantRequest restaurantRequest = buildValidRestaurantRequest();
        restaurantRequest.setRestaurantName("A"); // 1 character

        Set<ConstraintViolation<RestaurantRequest>> violations = validator.validate(restaurantRequest);
        assertFalse(violations.isEmpty());

    }

    @Test
    public void testInvalidRestaurantName() {
        RestaurantRequest restaurantRequest = buildValidRestaurantRequest();
        restaurantRequest.setRestaurantName("Test123"); // Contains numeric values

        Set<ConstraintViolation<RestaurantRequest>> violations = validator.validate(restaurantRequest);
        assertFalse(violations.isEmpty());

    }

    @Test
    public void testBlankRestaurantAddress() {
        RestaurantRequest restaurantRequest = buildValidRestaurantRequest();
        restaurantRequest.setRestaurantAddress("");

        Set<ConstraintViolation<RestaurantRequest>> violations = validator.validate(restaurantRequest);
        assertFalse(violations.isEmpty());

    }

    @Test
    public void testShortRestaurantAddress() {
        RestaurantRequest restaurantRequest = buildValidRestaurantRequest();
        restaurantRequest.setRestaurantAddress("Addr"); // Less than 5 characters

        Set<ConstraintViolation<RestaurantRequest>> violations = validator.validate(restaurantRequest);
        assertFalse(violations.isEmpty());

    }

    @Test
    public void testNullContactNumber() {
        RestaurantRequest restaurantRequest = buildValidRestaurantRequest();
        restaurantRequest.setContactNumber(null);

        Set<ConstraintViolation<RestaurantRequest>> violations = validator.validate(restaurantRequest);
        assertFalse(violations.isEmpty());

    }



    @Test
    public void testInvalidContactNumberFormat() {
        RestaurantRequest restaurantRequest = buildValidRestaurantRequest();
        restaurantRequest.setContactNumber("abcd123456"); // Invalid format

        Set<ConstraintViolation<RestaurantRequest>> violations = validator.validate(restaurantRequest);
        assertFalse(violations.isEmpty());
        //assertEquals("Contact Number must start with 6, 7, 8, or 9 and be 10 digits long", violations.iterator().next().getMessage());
    }

    @Test
    public void testBlankRestaurantDescription() {
        RestaurantRequest restaurantRequest = buildValidRestaurantRequest();
        restaurantRequest.setRestaurantDescription(""); // Blank description

        Set<ConstraintViolation<RestaurantRequest>> violations = validator.validate(restaurantRequest);
        assertFalse(violations.isEmpty());

        // Check for NotBlank constraint message
        boolean foundNotBlankViolation = violations.stream()
                .anyMatch(violation -> violation.getMessage().equals("Restaurant Description can not be blank"));

        assertTrue(foundNotBlankViolation);
    }

    @Test
    public void testShortRestaurantDescription() {
        RestaurantRequest restaurantRequest = buildValidRestaurantRequest();
        restaurantRequest.setRestaurantDescription("Desc"); // Less than 5 characters

        Set<ConstraintViolation<RestaurantRequest>> violations = validator.validate(restaurantRequest);
        assertFalse(violations.isEmpty());

        // Check for Size constraint message
        boolean foundSizeViolation = violations.stream()
                .anyMatch(violation -> violation.getMessage().equals("size must be between 5 and 15"));

        assertTrue(foundSizeViolation);
    }

    @Test
    public void testValidOpeningHour() {
        RestaurantRequest restaurantRequest = buildValidRestaurantRequest();
        restaurantRequest.setOpeningHour("08:00 AM"); // Assuming this is a valid input

        Set<ConstraintViolation<RestaurantRequest>> violations = validator.validate(restaurantRequest);

        // Print out violations to debug
        for (ConstraintViolation<RestaurantRequest> violation : violations) {
            System.out.println("Violation: " + violation.getMessage());
        }


    }


    private RestaurantRequest buildValidRestaurantRequest() {
        RestaurantRequest restaurantRequest = new RestaurantRequest();
        restaurantRequest.setUserId(1L);
        restaurantRequest.setRestaurantName("Valid Restaurant Name");
        restaurantRequest.setRestaurantAddress("123 Valid Address");
        restaurantRequest.setContactNumber("9876543210");
        restaurantRequest.setRestaurantDescription("Valid Description");
        restaurantRequest.setOpeningHour("10:00 AM");
        return restaurantRequest;
    }
}
