package com.orders.service;//package com.orders.service;

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
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

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

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void testCompleteOrder_Failure_OrderNotFound() {
        // Arrange
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act
        boolean result = orderService.completeOrder(orderId);

        // Assert
        assertFalse(result);
        verify(orderRepository, never()).save(any(Order.class));
    }


    @Test
    public void testGetOrdersByUserId_Success() {
        // Arrange
        Long userId = 1L;
        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setOrderId(1L);
        orders.add(order);
        when(orderRepository.findByUserId(userId)).thenReturn(orders);

        // Act
        List<OrderResponse> result = orderService.getOrdersByUserId(userId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getOrderId());
    }


    @Test
    public void testGetOrdersByUserId_NoOrdersFound() {
        // Arrange
        Long userId = 1L;
        when(orderRepository.findByUserId(userId)).thenReturn(new ArrayList<>());

        // Act
        ResourceNotFoundException thrownException = assertThrows(ResourceNotFoundException.class,
                () -> orderService.getOrdersByUserId(userId));

        // Assert
        assertEquals(ConstantMessages.NO_ORDERS_FOUND, thrownException.getMessage());
    }

    @Test
    public void testGetOrdersByRestaurantId() {
        // Arrange
        Long restaurantId = 1L;
        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setOrderId(1L);
        orders.add(order);
        when(orderRepository.findByRestaurantId(restaurantId)).thenReturn(orders);

        // Act
        List<Order> result = orderService.getOrdersByRestaurantId(restaurantId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getOrderId());
    }



    @Test
    public void testCancelOrder_Success() {
        // Arrange
        Long orderId = 1L;
        Order order = new Order();
        order.setOrderId(orderId);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderTime(LocalDateTime.now().minusSeconds(20)); // Order is within cancelable duration

        // Mock order repository response
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Mock user response
        UserResponse mockUserResponse = new UserResponse();
        mockUserResponse.setUserId(1L);
        mockUserResponse.setWallet(100.0);
        when(userFeignClient.getUserById(order.getUserId())).thenReturn(mockUserResponse);

        // Mocking order total price
        order.setTotalPrice(50.0);

        // Act
        boolean result = orderService.cancelOrder(orderId);

        // Assert
        assertTrue(result);
        verify(orderRepository, times(1)).save(order);
        verify(userFeignClient, times(1)).updateWalletBalance(order.getUserId(), mockUserResponse.getWallet() + order.getTotalPrice());
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
    public void testCreateOrderFromCart_Success() throws JsonProcessingException {
        // Arrange
        Long userId = 1L;
        Long addressId = 2L;

        // Mocking a valid user response with sufficient funds
        UserResponse mockUserResponse = new UserResponse();
        mockUserResponse.setUserId(userId);
        mockUserResponse.setWallet(300.0); // Sufficient funds
        when(userFeignClient.getUserById(userId)).thenReturn(mockUserResponse);

        // Mocking Cart items with a total price within wallet balance
        List<Cart> cartItems = new ArrayList<>();
        Cart cart = new Cart();
        cart.setFoodItemId(1L);
        cart.setQuantity(2);
        cart.setPricePerItem(100.0); // Total price = 200.0
        cart.setRestaurantId(1L);
        cartItems.add(cart);
        when(cartRepository.findByUserId(userId)).thenReturn(cartItems);

        // Mocking a valid restaurant response
        RestaurantResponse mockRestaurantResponse = new RestaurantResponse();
        when(restaurantFeignClient.getRestaurantById(cart.getRestaurantId())).thenReturn(mockRestaurantResponse);

        // Mocking valid address response
        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setAddressId(addressId);
        List<AddressResponse> addressList = new ArrayList<>();
        addressList.add(addressResponse);
        when(userFeignClient.getAllAddressesForUser(userId)).thenReturn(addressList);

        // Mocking CartResponse conversion
        CartResponse cartResponse = new CartResponse();

        when(dtoConversion.cartToCartResponse(any(Cart.class))).thenReturn(cartResponse);

        // Mocking ObjectMapper for JSON conversion
        String mockCartItemsJson = "mockJson";
        when(objectMapper.writeValueAsString(anyList())).thenReturn(mockCartItemsJson);

        // Mocking order repository save
        Order mockOrder = new Order();
        mockOrder.setOrderId(1L);
        mockOrder.setTotalPrice(200.0);
        mockOrder.setOrderStatus(OrderStatus.PENDING);
        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

        // Act
        OrderResponse result = orderService.createOrderFromCart(userId, addressId);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getOrderId());
        assertEquals(200.0, result.getTotalPrice());
        assertEquals(OrderStatus.PENDING, result.getOrderStatus());
        verify(userFeignClient, times(1)).updateWalletBalance(userId, 100.0); // 300 - 200 = 100
        verify(cartRepository, times(1)).deleteAll(cartItems); // Ensure cart is cleared
    }


    @Test
    public void testCreateOrderFromCart_JsonProcessingException() throws JsonProcessingException {
        // Arrange
        Long userId = 1L;
        Long addressId = 2L;

        // Mocking a valid user response with sufficient funds
        UserResponse mockUserResponse = new UserResponse();
        mockUserResponse.setUserId(userId);
        mockUserResponse.setWallet(300.0); // Sufficient funds
        when(userFeignClient.getUserById(userId)).thenReturn(mockUserResponse);

        // Mocking Cart items with valid data
        List<Cart> cartItems = new ArrayList<>();
        Cart cart = new Cart();
        cart.setFoodItemId(1L);
        cart.setQuantity(2);
        cart.setPricePerItem(100.0);
        cart.setRestaurantId(1L);
        cartItems.add(cart);
        when(cartRepository.findByUserId(userId)).thenReturn(cartItems);

        // Mocking a valid restaurant response
        RestaurantResponse mockRestaurantResponse = new RestaurantResponse();
        when(restaurantFeignClient.getRestaurantById(cart.getRestaurantId())).thenReturn(mockRestaurantResponse);

        // Mocking valid address response
        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setAddressId(addressId);
        List<AddressResponse> addressList = new ArrayList<>();
        addressList.add(addressResponse);
        when(userFeignClient.getAllAddressesForUser(userId)).thenReturn(addressList);

        // Simulating JSON processing exception
        when(objectMapper.writeValueAsString(anyList())).thenThrow(new JsonProcessingException("Error serializing") {});

    }


    @Test
    @Transactional
    public void testAddItemToCart_MultipleRestaurantsInCart() {
        CartRequest cartRequest = new CartRequest();
        cartRequest.setUserId(1L);
        cartRequest.setRestaurantId(2L); // New restaurant ID being added
        cartRequest.setFoodItemId(3L);
        cartRequest.setQuantity(1);

        // Mocking user validation
        when(userFeignClient.getUserById(cartRequest.getUserId())).thenReturn(new UserResponse());

        // Mocking restaurant validation
        when(restaurantFeignClient.getRestaurantById(cartRequest.getRestaurantId())).thenReturn(new RestaurantResponse());

        // Mocking food item validation
        when(restaurantFeignClient.getMenuItemById(cartRequest.getFoodItemId())).thenReturn(new RestaurantMenuResponse());

        // Mocking existing cart items from a different restaurant
        Cart existingCartItem = new Cart();
        existingCartItem.setRestaurantId(1L); // Existing restaurant ID different from the new one
        List<Cart> existingCartItems = Arrays.asList(existingCartItem);
        when(cartRepository.findByUserId(cartRequest.getUserId())).thenReturn(existingCartItems);

        // Test and assert exception
        Exception exception = assertThrows(InvalidRequestException.class, () -> {
            cartService.addItemToCart(cartRequest);
        });

        assertEquals(ConstantMessages.MULTIPLE_RESTAURANT_ERROR, exception.getMessage());
    }

    @Test
    public void testCreateOrderFromCart_RestaurantNotFound() throws JsonProcessingException {
        // Arrange
        Long userId = 1L;
        Long addressId = 2L;

        // Mocking a valid user response
        UserResponse mockUserResponse = new UserResponse();
        mockUserResponse.setUserId(userId);
        mockUserResponse.setWallet(300.0);
        when(userFeignClient.getUserById(userId)).thenReturn(mockUserResponse);

        // Mocking Cart items
        List<Cart> cartItems = new ArrayList<>();
        Cart cart = new Cart();
        cart.setFoodItemId(1L);
        cart.setQuantity(2);
        cart.setPricePerItem(100.0);
        cart.setRestaurantId(1L);
        cartItems.add(cart);
        when(cartRepository.findByUserId(userId)).thenReturn(cartItems);

        // Mocking restaurant response as null (restaurant not found)
        when(restaurantFeignClient.getRestaurantById(cart.getRestaurantId())).thenReturn(null);

        // Act & Assert: Expect a RuntimeException to be thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.createOrderFromCart(userId, addressId);
        });

        // Verify that the cause of RuntimeException is ResourceNotFoundException
        Throwable cause = exception.getCause();
        assertNotNull(cause);
        assertTrue(cause instanceof ResourceNotFoundException);
        assertEquals(ConstantMessages.INVALID_RESTAURANT_ID, cause.getMessage());

        // Verify that restaurantFeignClient and cartRepository methods were called
        verify(restaurantFeignClient, times(1)).getRestaurantById(cart.getRestaurantId());
        verify(cartRepository, times(1)).findByUserId(userId); // Ensure cartRepository is called
    }

}

