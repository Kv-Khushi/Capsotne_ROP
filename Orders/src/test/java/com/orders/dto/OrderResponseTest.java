package com.orders.dto;

import com.orders.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class OrderResponseTest {

    @Test
    public void testGettersAndSetters() {
        Long orderId = 1L;
        Double totalPrice = 99.99;
        OrderStatus orderStatus = OrderStatus.PENDING;
        LocalDateTime orderTime = LocalDateTime.now();
        String items = "[item1, item2]";

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(orderId);
        orderResponse.setTotalPrice(totalPrice);
        orderResponse.setOrderStatus(orderStatus);
        orderResponse.setOrderTime(orderTime);
        orderResponse.setItems(items);

        assertEquals(orderId, orderResponse.getOrderId());
        assertEquals(totalPrice, orderResponse.getTotalPrice());
        assertEquals(orderStatus, orderResponse.getOrderStatus());
        assertEquals(orderTime, orderResponse.getOrderTime());
        assertEquals(items, orderResponse.getItems());
    }

    private OrderResponse buildOrderResponse(Long orderId, Double totalPrice, OrderStatus orderStatus, LocalDateTime orderTime, String items) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(orderId);
        response.setTotalPrice(totalPrice);
        response.setOrderStatus(orderStatus);
        response.setOrderTime(orderTime);
        response.setItems(items);
        return response;
    }

    @Test
    public void testEqualsAndHashCode() {
        Long orderId = 1L;
        Double totalPrice = 99.99;
        OrderStatus orderStatus = OrderStatus.PENDING;
        LocalDateTime orderTime = LocalDateTime.now();
        String items = "[item1, item2]";

        OrderResponse response1 = buildOrderResponse(orderId, totalPrice, orderStatus, orderTime, items);
        OrderResponse response2 = buildOrderResponse(orderId, totalPrice, orderStatus, orderTime, items);

        // Test equals and hashCode with the same object
        assertEquals(response1, response1);
        assertEquals(response1.hashCode(), response1.hashCode());

        // Test not equals with a different object type
        assertNotEquals(response1, new Object());

        // Test equals and hashCode with the same values
        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());

        // Test not equals with different orderId
        response2 = buildOrderResponse(2L, totalPrice, orderStatus, orderTime, items);
        assertNotEquals(response1, response2);
        assertNotEquals(response1.hashCode(), response2.hashCode());

        // Test not equals with different totalPrice
        response2 = buildOrderResponse(orderId, 100.00, orderStatus, orderTime, items);
        assertNotEquals(response1, response2);
        assertNotEquals(response1.hashCode(), response2.hashCode());

        // Test not equals with different orderStatus
        response2 = buildOrderResponse(orderId, totalPrice, OrderStatus.COMPLETED, orderTime, items);
        assertNotEquals(response1, response2);
        assertNotEquals(response1.hashCode(), response2.hashCode());

        // Test not equals with different orderTime
        response2 = buildOrderResponse(orderId, totalPrice, orderStatus, orderTime.minusDays(1), items);
        assertNotEquals(response1, response2);
        assertNotEquals(response1.hashCode(), response2.hashCode());

        // Test not equals with different items
        response2 = buildOrderResponse(orderId, totalPrice, orderStatus, orderTime, "[item3]");
        assertNotEquals(response1, response2);
        assertNotEquals(response1.hashCode(), response2.hashCode());

        // Test equals with empty objects
        response1 = new OrderResponse();
        response2 = new OrderResponse();
        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }
    @Test
    public void testToString() {
        Long orderId = 1L;
        Double totalPrice = 99.99;
        OrderStatus orderStatus = OrderStatus.PENDING;
        LocalDateTime orderTime = LocalDateTime.now();
        String items = "[item1, item2]";

        OrderResponse orderResponse = buildOrderResponse(orderId, totalPrice, orderStatus, orderTime, items);

        String expectedToString = "OrderResponse(orderId=" + orderId + ", totalPrice=" + totalPrice +
                ", orderStatus=" + orderStatus + ", orderTime=" + orderTime +
                ", items=" + items + ")";
        assertEquals(expectedToString, orderResponse.toString());
    }
}
