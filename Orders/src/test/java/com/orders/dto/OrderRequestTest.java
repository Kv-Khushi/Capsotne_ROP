package com.orders.dto;

import com.orders.entities.Cart;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest
public class OrderRequestTest {

    @Test
    public void testGettersAndSetters() {
        OrderRequest orderRequest = new OrderRequest();

        // Test userId
        assertNull(orderRequest.getUserId());
        Long userId = 123L;
        orderRequest.setUserId(userId);
        assertEquals(userId, orderRequest.getUserId());

        // Test restaurantId
        assertNull(orderRequest.getRestaurantId());
        Long restaurantId = 456L;
        orderRequest.setRestaurantId(restaurantId);
        assertEquals(restaurantId, orderRequest.getRestaurantId());

        // Test addressId
        assertNull(orderRequest.getAddressId());
        Long addressId = 789L;
        orderRequest.setAddressId(addressId);
        assertEquals(addressId, orderRequest.getAddressId());

        // Test items
        assertNull(orderRequest.getItems());
        List<Cart> items = new ArrayList<>();
        items.add(new Cart()); // Add dummy Cart object
        orderRequest.setItems(items);
        assertEquals(items, orderRequest.getItems());
    }

    @Test
    public void testToString() {
        OrderRequest orderRequest = new OrderRequest();

        orderRequest.setUserId(123L);
        orderRequest.setRestaurantId(456L);
        orderRequest.setAddressId(789L);

        List<Cart> items = new ArrayList<>();
        items.add(new Cart()); // Add dummy Cart object
        orderRequest.setItems(items);

        assertEquals(
                "OrderRequest(userId=123, restaurantId=456, addressId=789, items=[Cart(cartId=null, userId=null, foodItemId=null, quantity=null, restaurantId=null, pricePerItem=null)])",
                orderRequest.toString());
    }



    private OrderRequest buildOrderRequest(Long userId, Long restaurantId, Long addressId, List<Cart> items) {
        OrderRequest request = new OrderRequest();
        request.setUserId(userId);
        request.setRestaurantId(restaurantId);
        request.setAddressId(addressId);
        request.setItems(items);
        return request;
    }
}
