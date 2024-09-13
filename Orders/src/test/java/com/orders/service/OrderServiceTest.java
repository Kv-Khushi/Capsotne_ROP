package com.orders.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.constant.ConstantMessages;
import com.orders.dto.*;
import com.orders.entities.Cart;
import com.orders.entities.Order;
import com.orders.enums.OrderStatus;
import com.orders.exception.InvalidRequestException;
import com.orders.exception.ResourceNotFoundException;
import com.orders.feignclientconfig.UserFeignClient;
import com.orders.feignclientconfig.RestaurantFeignClient;
import com.orders.repository.CartRepository;
import com.orders.repository.OrderRepository;
import com.orders.dtoconversion.DtoConversion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserFeignClient userFeignClient;

    @Mock
    private RestaurantFeignClient restaurantFeignClient;

    @Mock
    private DtoConversion dtoConversion;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Transactional
    public void testCreateOrderFromCart_Success() throws JsonProcessingException {
        // Arrange
        Long userId = 1L;
        Long addressId = 2L;

        // Mocking UserResponse
        UserResponse userResponse = new UserResponse();
        when(userFeignClient.getUserById(userId)).thenReturn(userResponse);

        // Mocking Cart items
        List<Cart> cartItems = new ArrayList<>();
        Cart cart = new Cart();
        cart.setFoodItemId(1L);
        cart.setQuantity(2);
        cart.setPricePerItem(100.0);
        cart.setRestaurantId(1L);
        cartItems.add(cart);
        when(cartRepository.findByUserId(userId)).thenReturn(cartItems);

        // Mocking RestaurantResponse
        RestaurantResponse restaurantResponse = new RestaurantResponse();
        when(restaurantFeignClient.getRestaurantById(any(Long.class))).thenReturn(restaurantResponse);

        // Mocking Address validation
        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setAddressId(addressId);
        List<AddressResponse> addressList = new ArrayList<>();
        addressList.add(addressResponse);
        when(userFeignClient.getAllAddressesForUser(userId)).thenReturn(addressList);

        // Mocking menu response
        RestaurantMenuResponse menuResponse = new RestaurantMenuResponse();
        menuResponse.setPrice(100.0);
        when(restaurantFeignClient.getMenuItemById(any(Long.class))).thenReturn(menuResponse);

        // Mocking Cart to CartResponse conversion
        CartResponse cartResponse = new CartResponse();
        when(dtoConversion.cartToCartResponse(any(Cart.class), any(RestaurantMenuResponse.class))).thenReturn(cartResponse);

        // Mocking ObjectMapper
        when(objectMapper.writeValueAsString(any())).thenReturn("[{\"cartResponse\":{}}]");

        // Mocking Order save
        Order savedOrder = new Order();
        savedOrder.setOrderId(1L);
        savedOrder.setTotalPrice(200.0);
        savedOrder.setOrderStatus(OrderStatus.PENDING);
        savedOrder.setOrderTime(LocalDateTime.now());
        savedOrder.setRestaurantId(1L);
        savedOrder.setAddressId(addressId);
        savedOrder.setItems("[{\"cartResponse\":{}}]");
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        // Act
        OrderResponse result = orderService.createOrderFromCart(userId, addressId);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getOrderId());
        assertEquals(200.0, result.getTotalPrice());
        assertEquals(OrderStatus.PENDING, result.getOrderStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(cartRepository, times(1)).deleteAll(cartItems);
    }

    @Test

    @Transactional
    public void testCreateOrderFromCart_NoItemsInCart() throws JsonProcessingException {
        // Arrange
        Long userId = 1L;
        Long addressId = 2L;

        // Mocking a valid user response
        UserResponse mockUserResponse = new UserResponse();
        mockUserResponse.setUserId(userId);
        when(userFeignClient.getUserById(userId)).thenReturn(mockUserResponse);

        // Mocking an empty cart list
        when(cartRepository.findByUserId(userId)).thenReturn(new ArrayList<>());

        // Act & Assert
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                () -> orderService.createOrderFromCart(userId, addressId));

        assertEquals(ConstantMessages.NO_ITEMS_IN_CART, exception.getMessage());
    }


    @Test
    @Transactional
    public void testCancelOrder_Success() {
        // Arrange
        Long orderId = 1L;

        Order order = new Order();
        order.setOrderId(orderId);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderTime(LocalDateTime.now().minusSeconds(20)); // Order is within cancelable duration

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        boolean result = orderService.cancelOrder(orderId);

        // Assert
        assertTrue(result);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    @Transactional
    public void testCancelOrder_Failure_OrderNotCancelable() {
        // Arrange
        Long orderId = 1L;

        Order order = new Order();
        order.setOrderId(orderId);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderTime(LocalDateTime.now().minusMinutes(1)); // Order is past cancelable time

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        boolean result = orderService.cancelOrder(orderId);

        // Assert
        assertFalse(result);
        verify(orderRepository, never()).save(order);
    }

    @Test
    public void testCreateOrderFromCart_InvalidUser() {
        // Arrange
        Long userId = 1L;
        Long addressId = 2L;

        when(userFeignClient.getUserById(userId)).thenReturn(null);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> orderService.createOrderFromCart(userId, addressId));

        assertEquals(ConstantMessages.INVALID_USER_ID, exception.getMessage());
    }
}
