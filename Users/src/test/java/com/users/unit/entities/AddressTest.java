package com.users.unit.entities;

import com.users.entities.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    private Address address;

    @BeforeEach
    public void setUp() {
        address = new Address();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(address);  // Verify the object is created
    }

    @Test
    public void testSettersAndGetters() {
        address.setAddressId(1L);
        address.setStreet("123 Main St");
        address.setCity("Sample City");
        address.setState("Sample State");
        address.setZipCode(12345);
        address.setCountry("Sample Country");
        address.setUserId(101L);

        assertEquals(1L, address.getAddressId());
        assertEquals("123 Main St", address.getStreet());
        assertEquals("Sample City", address.getCity());
        assertEquals("Sample State", address.getState());
        assertEquals(12345, address.getZipCode());
        assertEquals("Sample Country", address.getCountry());
        assertEquals(101L, address.getUserId());
    }

    @Test
    public void testEqualsAndHashCode() {
        Address address1 = new Address();
        address1.setAddressId(1L);
        address1.setStreet("123 Main St");
        address1.setCity("Sample City");
        address1.setState("Sample State");
        address1.setZipCode(12345);
        address1.setCountry("Sample Country");
        address1.setUserId(101L);

        Address address2 = new Address();
        address2.setAddressId(1L);
        address2.setStreet("123 Main St");
        address2.setCity("Sample City");
        address2.setState("Sample State");
        address2.setZipCode(12345);
        address2.setCountry("Sample Country");
        address2.setUserId(101L);


        assertEquals(address1, address2);
        assertEquals(address1.hashCode(), address2.hashCode());
    }

    @Test
    public void testNotEquals() {
        Address address1 = new Address();
        address1.setAddressId(1L);
        address1.setStreet("123 Main St");
        address1.setCity("Sample City");

        Address address2 = new Address();
        address2.setAddressId(2L);
        address2.setStreet("456 Another St");
        address2.setCity("Another City");


        assertNotEquals(address1, address2);
    }

    @Test
    public void testToString() {
        address.setAddressId(1L);
        address.setStreet("123 Main St");
        address.setCity("Sample City");
        address.setState("Sample State");
        address.setZipCode(12345);
        address.setCountry("Sample Country");
        address.setUserId(101L);

        String expectedString = "Address(addressId=1, street=123 Main St, city=Sample City, state=Sample State, zipCode=12345, country=Sample Country, userId=101)";
        assertEquals(expectedString, address.toString());
    }

    @Test
    public void testInvalidZipCode() {
        address.setZipCode(null);
        assertNull(address.getZipCode());

    }
}
