package com.orders.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class CartResponseTest {

    @Test
    public void testGettersAndSetters() {
        CartResponse cartResponse = new CartResponse();

        // Test cartId
        assertNull(cartResponse.getCartId());
        Long cartId = 456L;
        cartResponse.setCartId(cartId);
        assertEquals(cartId, cartResponse.getCartId());

        // Test foodItemId
        assertNull(cartResponse.getFoodItemId());
        Long foodItemId = 789L;
        cartResponse.setFoodItemId(foodItemId);
        assertEquals(foodItemId, cartResponse.getFoodItemId());

        // Test quantity
        assertNull(cartResponse.getQuantity());
        Integer quantity = 3;
        cartResponse.setQuantity(quantity);
        assertEquals(quantity, cartResponse.getQuantity());

        // Test pricePerItem
        assertNull(cartResponse.getPricePerItem());
        Double pricePerItem = 15.75;
        cartResponse.setPricePerItem(pricePerItem);
        assertEquals(pricePerItem, cartResponse.getPricePerItem());
    }

    @Test
    public void testToString() {
        CartResponse cartResponse = new CartResponse();

        cartResponse.setCartId(456L);
        cartResponse.setFoodItemId(789L);
        cartResponse.setQuantity(3);
        cartResponse.setPricePerItem(15.75);

        assertEquals(
                "CartResponse(cartId=456, foodItemId=789, quantity=3, pricePerItem=15.75)",
                cartResponse.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        Long cartId = 456L;
        Long foodItemId = 789L;
        Integer quantity = 3;
        Double pricePerItem = 15.75;

        CartResponse response1 = buildCartResponse(cartId, foodItemId, quantity, pricePerItem);
        CartResponse response2 = buildCartResponse(cartId, foodItemId, quantity, pricePerItem);

        // Test equals and hashCode with same object
        assertEquals(response1, response1);
        assertEquals(response1.hashCode(), response1.hashCode());

        // Test not equals with different object type
        assertNotEquals(response1, new Object());

        // Test equals and hashCode with same values
        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());

        // Test not equals with different cartId
        response2 = buildCartResponse(123L, foodItemId, quantity, pricePerItem);
        assertNotEquals(response1, response2);
        assertNotEquals(response1.hashCode(), response2.hashCode());

        // Test not equals with different foodItemId
        response2 = buildCartResponse(cartId, 456L, quantity, pricePerItem);
        assertNotEquals(response1, response2);
        assertNotEquals(response1.hashCode(), response2.hashCode());

        // Test not equals with different quantity
        response2 = buildCartResponse(cartId, foodItemId, 5, pricePerItem);
        assertNotEquals(response1, response2);
        assertNotEquals(response1.hashCode(), response2.hashCode());

        // Test not equals with different pricePerItem
        response2 = buildCartResponse(cartId, foodItemId, quantity, 20.00);
        assertNotEquals(response1, response2);
        assertNotEquals(response1.hashCode(), response2.hashCode());

        // Test equals with empty objects
        response1 = new CartResponse();
        response2 = new CartResponse();
        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    private CartResponse buildCartResponse(Long cartId, Long foodItemId, Integer quantity, Double pricePerItem) {
        CartResponse response = new CartResponse();
        response.setCartId(cartId);
        response.setFoodItemId(foodItemId);
        response.setQuantity(quantity);
        response.setPricePerItem(pricePerItem);
        return response;
    }
}
