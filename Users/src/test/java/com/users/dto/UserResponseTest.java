package com.users.dto;

import com.users.enums.UserRole;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserResponseTest {

    @Test
    public void testGettersAndSetters() {
        UserResponse userResponse = new UserResponse();


        assertNull(userResponse.getUserId());
        Long userId = 1L;
        userResponse.setUserId(userId);
        assertEquals(userId, userResponse.getUserId());


        assertNull(userResponse.getPhoneNumber());
        Long phoneNumber = 1234567890L;
        userResponse.setPhoneNumber(phoneNumber);
        assertEquals(phoneNumber, userResponse.getPhoneNumber());


        assertNull(userResponse.getUserName());
        String userName = "JohnDoe";
        userResponse.setUserName(userName);
        assertEquals(userName, userResponse.getUserName());


        assertNull(userResponse.getUserEmail());
        String userEmail = "john.doe@example.com";
        userResponse.setUserEmail(userEmail);
        assertEquals(userEmail, userResponse.getUserEmail());


        assertNull(userResponse.getUserPassword());
        String userPassword = "password123";
        userResponse.setUserPassword(userPassword);
        assertEquals(userPassword, userResponse.getUserPassword());


        assertNull(userResponse.getWallet());
        Double wallet = 1000.0;
        userResponse.setWallet(wallet);
        assertEquals(wallet, userResponse.getWallet());


        assertNull(userResponse.getUserRole());
        UserRole userRole = UserRole.CUSTOMER;
        userResponse.setUserRole(userRole);
        assertEquals(userRole, userResponse.getUserRole());
    }

    @Test
    public void testToString() {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(1L);
        userResponse.setPhoneNumber(1234567890L);
        userResponse.setUserName("JohnDoe");
        userResponse.setUserEmail("john.doe@example.com");
        userResponse.setUserPassword("password123");
        userResponse.setWallet(1000.0);
        userResponse.setUserRole(UserRole.CUSTOMER);

        String expectedString = "UserResponse(userId=1, phoneNumber=1234567890, userName=JohnDoe, userEmail=john.doe@example.com, userPassword=password123, wallet=1000.0, userRole=CUSTOMER)";
        assertEquals(expectedString, userResponse.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        UserResponse user1 = new UserResponse();
        user1.setUserId(1L);
        user1.setPhoneNumber(1234567890L);
        user1.setUserName("JohnDoe");
        user1.setUserEmail("john.doe@example.com");
        user1.setUserPassword("password123");
        user1.setWallet(1000.0);
        user1.setUserRole(UserRole.CUSTOMER);

        UserResponse user2 = new UserResponse();
        user2.setUserId(1L);
        user2.setPhoneNumber(1234567890L);
        user2.setUserName("JohnDoe");
        user2.setUserEmail("john.doe@example.com");
        user2.setUserPassword("password123");
        user2.setWallet(1000.0);
        user2.setUserRole(UserRole.CUSTOMER);

        // Test equals and hashCode with the same object
        assertEquals(user1, user1);
        assertEquals(user1.hashCode(), user1.hashCode());

        // Test equals and hashCode with different object types
        assertNotEquals(user1, new Object());

        // Test equals and hashCode with different values
        UserResponse user3 = new UserResponse();
        user3.setUserId(2L);
        user3.setPhoneNumber(9876543210L);
        user3.setUserName("JaneDoe");
        user3.setUserEmail("jane.doe@example.com");
        user3.setUserPassword("password456");
        user3.setWallet(2000.0);
        user3.setUserRole(UserRole.RESTAURANT_OWNER);

        assertNotEquals(user1, user3);
        assertNotEquals(user1.hashCode(), user3.hashCode());

        // Test equals with empty objects
        UserResponse user4 = new UserResponse();
        UserResponse user5 = new UserResponse();
        assertEquals(user4, user5);
        assertEquals(user4.hashCode(), user5.hashCode());
    }
}
