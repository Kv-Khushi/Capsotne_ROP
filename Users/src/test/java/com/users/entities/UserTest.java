package com.users.entities;

import com.users.constant.ConstantMessage;
import com.users.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(user);
    }

    @Test
    public void testSettersAndGetters() {
        user.setUserId(1L);
        user.setPhoneNumber(7894567890L);
        user.setUserName("testUser");
        user.setUserEmail("test@example.com");
        user.setUserPassword("password123");
        user.setWallet(5000.0);
        user.setUserRole(UserRole.RESTAURANT_OWNER);

        assertEquals(1L, user.getUserId());
        assertEquals(7894567890L, user.getPhoneNumber());
        assertEquals("testUser", user.getUserName());
        assertEquals("test@example.com", user.getUserEmail());
        assertEquals("password123", user.getUserPassword());
        assertEquals(5000.0, user.getWallet());
        assertEquals(UserRole.RESTAURANT_OWNER, user.getUserRole());
    }


    @Test
    public void testEqualsAndHashCode() {
        User user1 = new User();
        user1.setUserId(1L);
        user1.setPhoneNumber(7894567890L);
        user1.setUserName("testUser");
        user1.setUserEmail("test@example.com");
        user1.setUserPassword("password123");
        user1.setWallet(ConstantMessage.WALLET_AMOUNT);
        user1.setUserRole(UserRole.RESTAURANT_OWNER);

        User user2 = new User();
        user2.setUserId(1L);
        user2.setPhoneNumber(7894567890L);
        user2.setUserName("testUser");
        user2.setUserEmail("test@example.com");
        user2.setUserPassword("password123");
        user2.setWallet(ConstantMessage.WALLET_AMOUNT);
        user2.setUserRole(UserRole.RESTAURANT_OWNER);


        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testNotEquals() {
        User user1 = new User();
        user1.setUserId(1L);
        user1.setPhoneNumber(7894567890L);
        user1.setUserName("testUser");

        User user2 = new User();
        user2.setUserId(2L);
        user2.setPhoneNumber(9876543210L);
        user2.setUserName("anotherUser");

        assertNotEquals(user1, user2);
    }

    @Test
    public void testUserRoleEnum() {
        user.setUserRole(UserRole.RESTAURANT_OWNER);
        assertEquals(UserRole.RESTAURANT_OWNER, user.getUserRole());

        user.setUserRole(UserRole.CUSTOMER);
        assertEquals(UserRole.CUSTOMER, user.getUserRole());
    }

    @Test
    public void testInvalidEmail() {
        user.setUserEmail(null);
        assertNull(user.getUserEmail());
    }

    @Test
    public void testInvalidPhoneNumber() {
        user.setPhoneNumber(null);
        assertNull(user.getPhoneNumber());

    }
}
