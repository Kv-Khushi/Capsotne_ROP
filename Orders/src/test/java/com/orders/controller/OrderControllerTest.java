package com.orders.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.constant.ConstantMessages;
import com.orders.dto.MessageResponse;
import com.orders.dto.OrderResponse;
import com.orders.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Test createOrderFromCart with valid input
    @Test
    public void testCreateOrderFromCart_Success() throws JsonProcessingException {
        // Arrange
        Long userId = 1L;
        Long addressId = 2L;
        OrderResponse mockOrderResponse = new OrderResponse();
        when(orderService.createOrderFromCart(userId, addressId)).thenReturn(mockOrderResponse);

        // Act
        ResponseEntity<?> response = orderController.createOrderFromCart(userId, addressId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockOrderResponse, response.getBody());
        verify(orderService, times(1)).createOrderFromCart(userId, addressId);
    }

    // Test createOrderFromCart with IllegalArgumentException
    @Test
    public void testCreateOrderFromCart_IllegalArgumentException() throws JsonProcessingException {
        // Arrange
        Long userId = 1L;
        Long addressId = 2L;
        when(orderService.createOrderFromCart(userId, addressId)).thenThrow(new IllegalArgumentException("Invalid input"));

        // Act
        ResponseEntity<?> response = orderController.createOrderFromCart(userId, addressId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid input", response.getBody());
        verify(orderService, times(1)).createOrderFromCart(userId, addressId);
    }

    // Test createOrderFromCart with JsonProcessingException
    @Test
    public void testCreateOrderFromCart_JsonProcessingException() throws JsonProcessingException {
        // Arrange
        Long userId = 1L;
        Long addressId = 2L;
        when(orderService.createOrderFromCart(userId, addressId)).thenThrow(new JsonProcessingException("Error processing JSON") {});

        // Act
        ResponseEntity<?> response = orderController.createOrderFromCart(userId, addressId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error processing JSON", response.getBody());
        verify(orderService, times(1)).createOrderFromCart(userId, addressId);
    }

    // Test cancelOrder with successful cancellation
    @Test
    public void testCancelOrder_Success() {
        // Arrange
        Long orderId = 1L;
        when(orderService.cancelOrder(orderId)).thenReturn(true);

        // Act
        ResponseEntity<MessageResponse> response = orderController.cancelOrder(orderId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessages.ORDER_CANCELED_SUCCESSFULLY, response.getBody().getMessage());
        verify(orderService, times(1)).cancelOrder(orderId);
    }

    // Test cancelOrder with unsuccessful cancellation
    @Test
    public void testCancelOrder_Failure() {
        // Arrange
        Long orderId = 1L;
        when(orderService.cancelOrder(orderId)).thenReturn(false);

        // Act
        ResponseEntity<MessageResponse> response = orderController.cancelOrder(orderId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ConstantMessages.ORDER_CANNOT_BE_CANCELED, response.getBody().getMessage());
        verify(orderService, times(1)).cancelOrder(orderId);
    }

    @Test
    public void testCreateOrderFromCart_InvalidOrderResponse() throws JsonProcessingException {
        // Arrange
        Long userId = 1L;
        Long addressId = 2L;
        OrderResponse mockOrderResponse = new OrderResponse(); // or set it to a partially filled object
        when(orderService.createOrderFromCart(userId, addressId)).thenReturn(mockOrderResponse);

        // Act
        ResponseEntity<?> response = orderController.createOrderFromCart(userId, addressId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockOrderResponse, response.getBody());
        verify(orderService, times(1)).createOrderFromCart(userId, addressId);
    }

    @Test
    public void testCancelOrder_InvalidOrderId() {
        // Arrange
        Long orderId = -1L; // Invalid ID
        when(orderService.cancelOrder(orderId)).thenReturn(false);

        // Act
        ResponseEntity<MessageResponse> response = orderController.cancelOrder(orderId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ConstantMessages.ORDER_CANNOT_BE_CANCELED, response.getBody().getMessage());
        verify(orderService, times(1)).cancelOrder(orderId);
    }


}
