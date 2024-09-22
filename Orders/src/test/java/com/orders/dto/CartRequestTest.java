package com.orders.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CartRequestTest {

    @Test
    public void testGettersAndSetters() {
        CartRequest cartRequest = new CartRequest();

        // Test userId
        assertNull(cartRequest.getUserId());
        Long userId = 1L;
        cartRequest.setUserId(userId);
        assertEquals(userId, cartRequest.getUserId());

        // Test restaurantId
        assertNull(cartRequest.getRestaurantId());
        Long restaurantId = 101L;
        cartRequest.setRestaurantId(restaurantId);
        assertEquals(restaurantId, cartRequest.getRestaurantId());

        // Test cartId
        assertNull(cartRequest.getCartId());
        Long cartId = 202L;
        cartRequest.setCartId(cartId);
        assertEquals(cartId, cartRequest.getCartId());

        // Test quantity
        assertNull(cartRequest.getQuantity());
        Integer quantity = 5;
        cartRequest.setQuantity(quantity);
        assertEquals(quantity, cartRequest.getQuantity());

        // Test pricePerItem
        assertNull(cartRequest.getPricePerItem());
        Double pricePerItem = 10.5;
        cartRequest.setPricePerItem(pricePerItem);
        assertEquals(pricePerItem, cartRequest.getPricePerItem());

        // Test foodItemId
        assertNull(cartRequest.getFoodItemId());
        Long foodItemId = 303L;
        cartRequest.setFoodItemId(foodItemId);
        assertEquals(foodItemId, cartRequest.getFoodItemId());
    }

    @Test
    public void testToString() {
        CartRequest cartRequest = new CartRequest();

        cartRequest.setUserId(1L);
        cartRequest.setRestaurantId(101L);
        cartRequest.setCartId(202L);
        cartRequest.setQuantity(5);
        cartRequest.setPricePerItem(10.5);
        cartRequest.setFoodItemId(303L);

        assertEquals(
                "CartRequest(userId=1, restaurantId=101, cartId=202, quantity=5, pricePerItem=10.5, foodItemId=303)",
                cartRequest.toString()
        );
    }

    @Test
    public void testEqualsAndHashCode() {
        Long userId = 1L;
        Long restaurantId = 101L;
        Long cartId = 202L;
        Integer quantity = 5;
        Double pricePerItem = 10.5;
        Long foodItemId = 303L;

        CartRequest request1 = buildCartRequest(userId, restaurantId, cartId, quantity, pricePerItem, foodItemId);
        CartRequest request2 = buildCartRequest(userId, restaurantId, cartId, quantity, pricePerItem, foodItemId);

        // Test equals and hashCode with same object
        assertEquals(request1, request1);
        assertEquals(request1.hashCode(), request1.hashCode());

        // Test not equals with different object type
        assertNotEquals(request1, new Object());

        // Test equals and hashCode with same values
        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());

        // Test not equals with different userId
        request2 = buildCartRequest(2L, restaurantId, cartId, quantity, pricePerItem, foodItemId);
        assertNotEquals(request1, request2);
        assertNotEquals(request1.hashCode(), request2.hashCode());

        // Test not equals with different restaurantId
        request2 = buildCartRequest(userId, 102L, cartId, quantity, pricePerItem, foodItemId);
        assertNotEquals(request1, request2);
        assertNotEquals(request1.hashCode(), request2.hashCode());

        // Test not equals with different cartId
        request2 = buildCartRequest(userId, restaurantId, 303L, quantity, pricePerItem, foodItemId);
        assertNotEquals(request1, request2);
        assertNotEquals(request1.hashCode(), request2.hashCode());

        // Test not equals with different quantity
        request2 = buildCartRequest(userId, restaurantId, cartId, 10, pricePerItem, foodItemId);
        assertNotEquals(request1, request2);
        assertNotEquals(request1.hashCode(), request2.hashCode());

        // Test not equals with different pricePerItem
        request2 = buildCartRequest(userId, restaurantId, cartId, quantity, 20.0, foodItemId);
        assertNotEquals(request1, request2);
        assertNotEquals(request1.hashCode(), request2.hashCode());

        // Test not equals with different foodItemId
        request2 = buildCartRequest(userId, restaurantId, cartId, quantity, pricePerItem, 404L);
        assertNotEquals(request1, request2);
        assertNotEquals(request1.hashCode(), request2.hashCode());
    }

    private CartRequest buildCartRequest(Long userId, Long restaurantId, Long cartId, Integer quantity, Double pricePerItem, Long foodItemId) {
        CartRequest request = new CartRequest();
        request.setUserId(userId);
        request.setRestaurantId(restaurantId);
        request.setCartId(cartId);
        request.setQuantity(quantity);
        request.setPricePerItem(pricePerItem);
        request.setFoodItemId(foodItemId);
        return request;
    }
}
