package com.users.dto;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AddressRequestTest {

    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static Validator validator = factory.getValidator();

    @Test
    public void testValidAddressRequest() {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setStreet("123 Main St");
        addressRequest.setCity("Springfield");
        addressRequest.setState("IL");
        addressRequest.setZipCode(62701);
        addressRequest.setCountry("USA");
        addressRequest.setUserId(1L);

        Set<ConstraintViolation<AddressRequest>> violations = validator.validate(addressRequest);
        assertTrue(violations.isEmpty(), "Expected no validation errors.");
    }

    @Test
    public void testInvalidAddressRequest() {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setStreet(null);
        addressRequest.setCity("");
        addressRequest.setState("IL");
        addressRequest.setZipCode(null);
        addressRequest.setCountry("");
        addressRequest.setUserId(null);

        Set<ConstraintViolation<AddressRequest>> violations = validator.validate(addressRequest);

        assertEquals(5, violations.size(), "Expected 5 validation errors.");

        for (ConstraintViolation<AddressRequest> violation : violations) {
            String message = violation.getMessage();
            assertTrue(message.equals("Street is mandatory") ||
                            message.equals("City is mandatory") ||
                            message.equals("Zip code is mandatory") ||
                            message.equals("Country is mandatory") ||
                            message.equals("User ID is mandatory"),
                    "Unexpected validation error: " + message);
        }
    }

    @Test
    public void testGettersAndSetters() {
        AddressRequest addressRequest = new AddressRequest();

        // Test street
        assertNull(addressRequest.getStreet());
        addressRequest.setStreet("123 Main St");
        assertEquals("123 Main St", addressRequest.getStreet());

        // Test city
        assertNull(addressRequest.getCity());
        addressRequest.setCity("Springfield");
        assertEquals("Springfield", addressRequest.getCity());

        // Test state
        assertNull(addressRequest.getState());
        addressRequest.setState("IL");
        assertEquals("IL", addressRequest.getState());

        // Test zipCode
        assertNull(addressRequest.getZipCode());
        addressRequest.setZipCode(62701);
        assertEquals(62701, addressRequest.getZipCode());

        // Test country
        assertNull(addressRequest.getCountry());
        addressRequest.setCountry("USA");
        assertEquals("USA", addressRequest.getCountry());

        // Test userId
        assertNull(addressRequest.getUserId());
        addressRequest.setUserId(1L);
        assertEquals(1L, addressRequest.getUserId());
    }
}
