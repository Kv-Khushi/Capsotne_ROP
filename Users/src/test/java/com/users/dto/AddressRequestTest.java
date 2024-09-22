package com.users.dto;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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
    public void testGettersAndSetters() {
        AddressRequest addressRequest = new AddressRequest();


        assertNull(addressRequest.getStreet());
        addressRequest.setStreet("123 Main St");
        assertEquals("123 Main St", addressRequest.getStreet());


        assertNull(addressRequest.getCity());
        addressRequest.setCity("Springfield");
        assertEquals("Springfield", addressRequest.getCity());


        assertNull(addressRequest.getState());
        addressRequest.setState("IL");
        assertEquals("IL", addressRequest.getState());

        assertNull(addressRequest.getZipCode());
        addressRequest.setZipCode(62701);
        assertEquals(62701, addressRequest.getZipCode());


        assertNull(addressRequest.getCountry());
        addressRequest.setCountry("USA");
        assertEquals("USA", addressRequest.getCountry());


        assertNull(addressRequest.getUserId());
        addressRequest.setUserId(1L);
        assertEquals(1L, addressRequest.getUserId());
    }
}
