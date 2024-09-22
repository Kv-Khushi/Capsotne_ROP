package com.users.dtoconversion;

import com.users.dto.AddressRequest;
import com.users.dto.AddressResponse;
import com.users.dto.UserRequest;
import com.users.dto.UserResponse;
import com.users.entities.Address;
import com.users.entities.User;
import org.junit.jupiter.api.Test;
import com.users.enums.UserRole;

import static org.junit.jupiter.api.Assertions.*;

class DtoConversionTest {

    @Test
    void convertUserRequestToUser_shouldConvertSuccessfully() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName("John Doe");
        userRequest.setUserPassword("password123");
        userRequest.setPhoneNumber(1234567890L);
        userRequest.setUserEmail("john.doe@example.com");
        userRequest.setUserRole(UserRole.CUSTOMER);

        User user = DtoConversion.convertUserRequestToUser(userRequest);

        assertNotNull(user);
        assertEquals("John Doe", user.getUserName());
        assertEquals("password123", user.getUserPassword());
        assertEquals(1234567890L, user.getPhoneNumber());
        assertEquals("john.doe@example.com", user.getUserEmail());
        assertEquals(UserRole.CUSTOMER, user.getUserRole());
    }

    @Test
    void userToUserResponse_shouldConvertSuccessfully() {
        User user = new User();
        user.setUserId(1L);
        user.setUserName("John Doe");
        user.setPhoneNumber(1234567890L);
        user.setUserEmail("john.doe@example.com");

        UserResponse userResponse = DtoConversion.userToUserResponse(user);

        assertNotNull(userResponse);
        assertEquals(1L, userResponse.getUserId());
        assertEquals("John Doe", userResponse.getUserName());
        assertEquals(1234567890L, userResponse.getPhoneNumber());
        assertEquals("john.doe@example.com", userResponse.getUserEmail());
    }

    @Test
    void convertAddressRequestToAddress_shouldConvertSuccessfully() {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setStreet("123 Main St");
        addressRequest.setCity("Anytown");
        addressRequest.setState("CA");
        addressRequest.setZipCode(12345);  // Use Integer if zipCode is Integer
        addressRequest.setCountry("USA");
        addressRequest.setUserId(1L);

        Address address = DtoConversion.convertAddressRequestToAddress(addressRequest);

        assertNotNull(address);
        assertEquals("123 Main St", address.getStreet());
        assertEquals("Anytown", address.getCity());
        assertEquals("CA", address.getState());
        assertEquals(12345, address.getZipCode());  // Use Integer if zipCode is Integer
        assertEquals("USA", address.getCountry());
        assertEquals(1L, address.getUserId());
    }
    @Test
    void addressToAddressResponse_shouldConvertSuccessfully() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setZipCode(12345); // Integer
        address.setCountry("USA");
        address.setUserId(1L);

        AddressResponse addressResponse = DtoConversion.addressToAddressResponse(address);

        assertNotNull(addressResponse);
        assertEquals("123 Main St", addressResponse.getStreet());
        assertEquals("Anytown", addressResponse.getCity());
        assertEquals("CA", addressResponse.getState());
        assertEquals(12345, addressResponse.getZipCode()); // Integer
        assertEquals("USA", addressResponse.getCountry());
        assertEquals(1L, addressResponse.getUserId());
    }
}

