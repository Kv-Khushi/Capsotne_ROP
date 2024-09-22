package com.orders.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.orders.constant.ConstantMessages;
import com.orders.entities.Cart;
import com.orders.entities.Order;
import com.orders.enums.OrderStatus;
import com.orders.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javafx.beans.binding.Bindings.when;
import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    private Order order;

    @BeforeEach
    public void setup() {
        order = new Order();
        order.setOrderId(1L);
        order.setUserId(100L);
        order.setRestaurantId(200L);
        order.setAddressId(300L);
        order.setTotalPrice(50.0);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderTime(LocalDateTime.now());
    }




    @Test
    public void testOrderEntityCreation() {
        // Validate the entity fields
        assertEquals(1L, order.getOrderId());
        assertEquals(100L, order.getUserId());
        assertEquals(200L, order.getRestaurantId());
        assertEquals(300L, order.getAddressId());
        assertEquals(50.0, order.getTotalPrice());
        assertEquals(OrderStatus.PENDING, order.getOrderStatus());
        assertNotNull(order.getOrderTime());
    }

    @Test
    public void testSetCartItemsFromList() throws JsonProcessingException {
        // Create a list of Cart items
        List<Cart> cartItems = new ArrayList<>();
        Cart cartItem1 = new Cart();
        cartItem1.setCartId(1L);
        cartItem1.setUserId(100L);
        cartItem1.setFoodItemId(10L);
        cartItem1.setQuantity(2);
        cartItem1.setRestaurantId(200L);
        cartItem1.setPricePerItem(15.5);

        Cart cartItem2 = new Cart();
        cartItem2.setCartId(2L);
        cartItem2.setUserId(100L);
        cartItem2.setFoodItemId(20L);
        cartItem2.setQuantity(1);
        cartItem2.setRestaurantId(200L);
        cartItem2.setPricePerItem(30.0);

        cartItems.add(cartItem1);
        cartItems.add(cartItem2);

        // Set items as JSON string
        order.setCartItemsFromList(cartItems);

        // Validate the items JSON string
        assertNotNull(order.getItems());
        assertTrue(order.getItems().contains("\"cartId\":1"));
        assertTrue(order.getItems().contains("\"cartId\":2"));
    }

    @Test
    public void testGetCartItemsAsList() throws JsonProcessingException {
        // Set the JSON string for items
        String itemsJson = "[{\"cartId\":1,\"userId\":100,\"foodItemId\":10,\"quantity\":2,\"restaurantId\":200,\"pricePerItem\":15.5}," +
                "{\"cartId\":2,\"userId\":100,\"foodItemId\":20,\"quantity\":1,\"restaurantId\":200,\"pricePerItem\":30.0}]";
        order.setItems(itemsJson);

        // Convert JSON string back to List<Cart>
        List<Cart> cartItems = order.getCartItemsAsList();

        // Validate the list
        assertNotNull(cartItems);
        assertEquals(2, cartItems.size());

        Cart cartItem1 = cartItems.get(0);
        Cart cartItem2 = cartItems.get(1);

        // Validate first cart item
        assertEquals(1L, cartItem1.getCartId());
        assertEquals(100L, cartItem1.getUserId());
        assertEquals(10L, cartItem1.getFoodItemId());
        assertEquals(2, cartItem1.getQuantity());
        assertEquals(200L, cartItem1.getRestaurantId());
        assertEquals(15.5, cartItem1.getPricePerItem());

        // Validate second cart item
        assertEquals(2L, cartItem2.getCartId());
        assertEquals(100L, cartItem2.getUserId());
        assertEquals(20L, cartItem2.getFoodItemId());
        assertEquals(1, cartItem2.getQuantity());
        assertEquals(200L, cartItem2.getRestaurantId());
        assertEquals(30.0, cartItem2.getPricePerItem());
    }

    @Test
    public void testOrderEqualsAndHashCode() {
        // Create two orders with the same data
        Order order1 = new Order();
        order1.setOrderId(1L);
        order1.setUserId(100L);
        order1.setRestaurantId(200L);
        order1.setAddressId(300L);
        order1.setTotalPrice(50.0);
        order1.setOrderStatus(OrderStatus.PENDING);
        order1.setOrderTime(LocalDateTime.now());

        Order order2 = new Order();
        order2.setOrderId(1L);
        order2.setUserId(100L);
        order2.setRestaurantId(200L);
        order2.setAddressId(300L);
        order2.setTotalPrice(50.0);
        order2.setOrderStatus(OrderStatus.PENDING);
        order2.setOrderTime(order1.getOrderTime()); // Set to the same time for equality check

        // Validate equality and hashcode
        assertEquals(order1, order2);
        assertEquals(order1.hashCode(), order2.hashCode());
    }

}
