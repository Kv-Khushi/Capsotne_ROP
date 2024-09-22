package com.orders.controller;

import com.orders.constant.ConstantMessages;
import com.orders.dto.CartRequest;
import com.orders.dto.CartResponse;
import com.orders.dto.MessageResponse;
import com.orders.entities.Cart;
import com.orders.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Test addItemToCart
    @Test
    public void testAddItemToCart_Success() {
        // Arrange
        CartRequest cartRequest = new CartRequest();
        Cart mockCart = new Cart();
        when(cartService.addItemToCart(any(CartRequest.class))).thenReturn(mockCart);

        // Act
        ResponseEntity<?> response = cartController.addItemToCart(cartRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockCart, response.getBody());
        verify(cartService, times(1)).addItemToCart(any(CartRequest.class));
    }

    // Test removeItemFromCart
    @Test
    public void testRemoveItemFromCart_Success() {
        // Arrange
        Long userId = 1L;
        Long foodItemId = 1L;
        doNothing().when(cartService).removeItemFromCart(userId, foodItemId);

        // Act
        ResponseEntity<MessageResponse> response = cartController.removeItemFromCart(userId, foodItemId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ConstantMessages.ITEM_REMOVED_SUCCESSFULLY, response.getBody().getMessage());
        verify(cartService, times(1)).removeItemFromCart(userId, foodItemId);
    }

    // Test getAllCartItemsByUserId
    @Test
    public void testGetAllCartItemsByUserId_Success() {
        // Arrange
        Long userId = 1L;
        List<CartResponse> mockCartList = new ArrayList<>();
        when(cartService.getAllCartItemsByUserId(userId)).thenReturn(mockCartList);

        // Act
        ResponseEntity<List<CartResponse>> response = cartController.getAllCartItemsByUserId(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCartList, response.getBody());
        verify(cartService, times(1)).getAllCartItemsByUserId(userId);
    }
    @Test
    public void testGetAllCartItemsByUserId_EmptyList() {
        // Arrange
        Long userId = 1L;
        List<CartResponse> emptyCartList = new ArrayList<>();
        when(cartService.getAllCartItemsByUserId(userId)).thenReturn(emptyCartList);

        // Act
        ResponseEntity<List<CartResponse>> response = cartController.getAllCartItemsByUserId(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emptyCartList, response.getBody());
        verify(cartService, times(1)).getAllCartItemsByUserId(userId);
    }

}
