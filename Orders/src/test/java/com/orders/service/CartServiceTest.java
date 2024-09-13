package com.orders.service;

import com.orders.constant.ConstantMessages;
import com.orders.dtoconversion.DtoConversion;
import com.orders.entities.Cart;
import com.orders.exception.ResourceNotFoundException;
import com.orders.feignclientconfig.RestaurantFeignClient;
import com.orders.feignclientconfig.UserFeignClient;
import com.orders.dto.CartRequest;
import com.orders.dto.CartResponse;
import com.orders.dto.RestaurantMenuResponse;
import com.orders.dto.RestaurantResponse;
import com.orders.dto.UserResponse;
import com.orders.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private DtoConversion dtoConversion;

    @Mock
    private RestaurantFeignClient restaurantFeignClient;

    @Mock
    private UserFeignClient userFeignClient;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddItemToCart_Success() {
        // Arrange
        CartRequest cartRequest = new CartRequest();
        cartRequest.setUserId(1L);
        cartRequest.setRestaurantId(1L);
        cartRequest.setFoodItemId(1L);
        cartRequest.setQuantity(2);

        // Mocking the UserResponse from the user service
        UserResponse userResponse = new UserResponse();
        when(userFeignClient.getUserById(cartRequest.getUserId())).thenReturn(userResponse);

        // Mocking the RestaurantResponse from the restaurant service
        RestaurantResponse restaurantResponse = new RestaurantResponse();
        when(restaurantFeignClient.getRestaurantById(cartRequest.getRestaurantId())).thenReturn(restaurantResponse);

        // Mocking the RestaurantMenuResponse for the food item details
        RestaurantMenuResponse menuResponse = new RestaurantMenuResponse();
        menuResponse.setPrice(100.0);
        when(restaurantFeignClient.getMenuItemById(cartRequest.getFoodItemId())).thenReturn(menuResponse);

        // Mocking the cart repository response
        List<Cart> existingCartItems = new ArrayList<>();
        when(cartRepository.findByUserId(cartRequest.getUserId())).thenReturn(existingCartItems);

        // Mocking the DTO conversion
        Cart newCart = new Cart();
        when(dtoConversion.cartRequestToCart(cartRequest)).thenReturn(newCart);
        when(cartRepository.save(any(Cart.class))).thenReturn(newCart);

        // Act
        Cart result = cartService.addItemToCart(cartRequest);

        // Assert
        assertNotNull(result);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    public void testAddItemToCart_InvalidUser() {
        // Arrange
        CartRequest cartRequest = new CartRequest();
        cartRequest.setUserId(1L);
        when(userFeignClient.getUserById(cartRequest.getUserId())).thenThrow(new ResourceNotFoundException(ConstantMessages.INVALID_USER_ID));

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> cartService.addItemToCart(cartRequest));
        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    public void testAddItemToCart_InvalidRestaurant() {
        // Arrange
        CartRequest cartRequest = new CartRequest();
        cartRequest.setUserId(1L);
        cartRequest.setRestaurantId(1L);

        UserResponse userResponse = new UserResponse();
        when(userFeignClient.getUserById(cartRequest.getUserId())).thenReturn(userResponse);

        when(restaurantFeignClient.getRestaurantById(cartRequest.getRestaurantId()))
                .thenThrow(new ResourceNotFoundException(ConstantMessages.INVALID_RESTAURANT_ID));

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> cartService.addItemToCart(cartRequest));
        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    public void testRemoveItemFromCart_Success() {
        // Arrange
        Long userId = 1L;
        Long foodItemId = 1L;
        Cart cart = new Cart();
        when(cartRepository.findByUserIdAndFoodItemId(userId, foodItemId)).thenReturn(Optional.of(cart));

        // Act
        cartService.removeItemFromCart(userId, foodItemId);

        // Assert
        verify(cartRepository, times(1)).delete(cart);
    }

    @Test
    public void testRemoveItemFromCart_ItemNotFound() {
        // Arrange
        Long userId = 1L;
        Long foodItemId = 1L;
        when(cartRepository.findByUserIdAndFoodItemId(userId, foodItemId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> cartService.removeItemFromCart(userId, foodItemId));
        verify(cartRepository, never()).delete(any(Cart.class));
    }

    @Test
    public void testUpdateItemQuantity_Success() {
        // Arrange
        CartRequest cartRequest = new CartRequest();
        cartRequest.setUserId(1L);
        cartRequest.setFoodItemId(1L);
        cartRequest.setQuantity(3);

        Cart cart = new Cart();
        when(cartRepository.findByUserIdAndFoodItemId(cartRequest.getUserId(), cartRequest.getFoodItemId()))
                .thenReturn(Optional.of(cart));

        RestaurantMenuResponse menuResponse = new RestaurantMenuResponse();
        menuResponse.setPrice(50.0);
        when(restaurantFeignClient.getMenuItemById(cartRequest.getFoodItemId())).thenReturn(menuResponse);

        // Act
        cartService.updateItemQuantity(cartRequest);

        // Assert
        assertEquals(3, cart.getQuantity());
        assertEquals(150.0, cart.getPricePerItem()); // Price per item * quantity
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    public void testUpdateItemQuantity_ItemNotFound() {
        // Arrange
        CartRequest cartRequest = new CartRequest();
        cartRequest.setUserId(1L);
        cartRequest.setFoodItemId(1L);
        when(cartRepository.findByUserIdAndFoodItemId(cartRequest.getUserId(), cartRequest.getFoodItemId()))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> cartService.updateItemQuantity(cartRequest));
        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    public void testGetAllCartItemsByUserId_Success() {
        // Arrange
        Long userId = 1L;
        List<Cart> cartItems = new ArrayList<>();
        Cart cart = new Cart();
        cartItems.add(cart);

        when(cartRepository.findByUserId(userId)).thenReturn(cartItems);

        CartResponse cartResponse = new CartResponse();
        RestaurantMenuResponse menuResponse = new RestaurantMenuResponse();
        when(dtoConversion.cartToCartResponse(cart, menuResponse)).thenReturn(cartResponse);
        when(restaurantFeignClient.getMenuItemById(cart.getFoodItemId())).thenReturn(menuResponse);

        // Act
        List<CartResponse> result = cartService.getAllCartItemsByUserId(userId);

        // Assert
        assertEquals(1, result.size());
        verify(cartRepository, times(1)).findByUserId(userId);
    }
}
