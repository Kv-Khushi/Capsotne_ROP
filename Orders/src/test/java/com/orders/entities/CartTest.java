package com.orders.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CartTest {

    @Test
    public void testCartEntityCreation() {
        // Create a Cart object
        Cart cart = new Cart();
        cart.setCartId(1L);
        cart.setUserId(100L);
        cart.setFoodItemId(50L);
        cart.setQuantity(3);
        cart.setRestaurantId(200L);
        cart.setPricePerItem(15.5);

        // Test the Cart object properties
        assertEquals(1L, cart.getCartId());
        assertEquals(100L, cart.getUserId());
        assertEquals(50L, cart.getFoodItemId());
        assertEquals(3, cart.getQuantity());
        assertEquals(200L, cart.getRestaurantId());
        assertEquals(15.5, cart.getPricePerItem());
    }

    @Test
    public void testCartEmptyConstructor() {
        // Create an empty Cart object using the default constructor
        Cart cart = new Cart();

        // Verify that the fields are null/zero as expected
        assertNull(cart.getCartId());
        assertNull(cart.getUserId());
        assertNull(cart.getFoodItemId());
        assertNull(cart.getRestaurantId());
        assertNull(cart.getPricePerItem());
        assertNull(cart.getQuantity());
    }

    @Test
    public void testCartSettersAndGetters() {
        // Create a Cart object
        Cart cart = new Cart();

        // Set properties
        cart.setCartId(1L);
        cart.setUserId(100L);
        cart.setFoodItemId(50L);
        cart.setQuantity(2);
        cart.setRestaurantId(200L);
        cart.setPricePerItem(20.0);

        // Test getters
        assertEquals(1L, cart.getCartId());
        assertEquals(100L, cart.getUserId());
        assertEquals(50L, cart.getFoodItemId());
        assertEquals(2, cart.getQuantity());
        assertEquals(200L, cart.getRestaurantId());
        assertEquals(20.0, cart.getPricePerItem());
    }

    @Test
    public void testCartEqualsAndHashCode() {
        // Create two Cart objects with the same data
        Cart cart1 = new Cart();
        cart1.setCartId(1L);
        cart1.setUserId(100L);
        cart1.setFoodItemId(50L);
        cart1.setQuantity(3);
        cart1.setRestaurantId(200L);
        cart1.setPricePerItem(15.5);

        Cart cart2 = new Cart();
        cart2.setCartId(1L);
        cart2.setUserId(100L);
        cart2.setFoodItemId(50L);
        cart2.setQuantity(3);
        cart2.setRestaurantId(200L);
        cart2.setPricePerItem(15.5);

        // Test equality
        assertEquals(cart1, cart2);
        assertEquals(cart1.hashCode(), cart2.hashCode());
    }
}
