package com.orders.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.entities.Cart;
import com.orders.entities.Order;
import com.orders.enums.OrderStatus;

import com.orders.outdto.*;
import com.orders.repository.CartRepository;
import com.orders.repository.OrderRepository;
import com.orders.feignclientconfig.UserFeignClient;
import com.orders.feignclientconfig.RestaurantFeignClient;
import com.orders.dtoconversion.DtoConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private RestaurantFeignClient restaurantFeignClient;

    @Autowired
    private DtoConversion dtoConversion;

    @Autowired
    private ObjectMapper objectMapper; // Ensure ObjectMapper is autowired

    public OrderResponse createOrderFromCart(Long userId, Long addressId) throws JsonProcessingException {
        // Fetch the user from the user service
        UserResponse userResponse = userFeignClient.getUserById(userId);
        if (userResponse == null) {
            throw new IllegalArgumentException("User not found.");
        }

        // Fetch all cart items for the user
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("No items found in the cart.");
        }

        // Fetch the restaurant details from the restaurant service
        Long restaurantId = cartItems.get(0).getRestaurantId();
        RestaurantResponse restaurantResponse = restaurantFeignClient.getRestaurantById(restaurantId);
        if (restaurantResponse == null) {
            throw new IllegalArgumentException("Restaurant not found.");
        }

        // Fetch all addresses for the user
        List<AddressResponse> addresses = userFeignClient.getAllAddressesForUser(userId);

        // Validate that the provided addressId belongs to the user
        boolean addressBelongsToUser = addresses.stream()
                .anyMatch(address -> address.getAddressId().equals(addressId));

        if (!addressBelongsToUser) {
            throw new IllegalArgumentException("Invalid address ID for this user.");
        }

        // Create a new order
        Order newOrder = new Order();
        newOrder.setUserId(userId);
        newOrder.setRestaurantId(restaurantId);
        newOrder.setAddressId(addressId);  // Set the validated address ID
        newOrder.setOrderTime(LocalDateTime.now());
        newOrder.setOrderStatus(OrderStatus.PENDING);

        // Calculate the total price
        double totalPrice = cartItems.stream()
                .mapToDouble(cart -> cart.getPricePerItem() * cart.getQuantity())
                .sum();
        newOrder.setTotalPrice(totalPrice);

        // Convert Cart items to CartResponse DTOs
        List<CartResponse> cartResponses = cartItems.stream()
                .map(cart -> {
                    // Fetch menu response for each cart item
                    RestaurantMenuResponse menuResponse = restaurantFeignClient.getMenuItemById(cart.getFoodItemId());
                    return dtoConversion.cartToCartResponse(cart, menuResponse);
                })
                .collect(Collectors.toList());

        // Serialize cart items to JSON string
        String cartItemsJson = objectMapper.writeValueAsString(cartResponses);
        // Log the JSON to ensure it's correct
        System.out.println("Serialized cart items JSON: " + cartItemsJson);

        // Set the serialized cart items JSON in the new order
        newOrder.setItems(cartItemsJson);

        // Save the order in the database
        newOrder = orderRepository.save(newOrder);

        // Remove all cart items after creating the order
        cartRepository.deleteAll(cartItems);

        // Create OrderResponse DTO
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(newOrder.getOrderId());
        orderResponse.setTotalPrice(totalPrice);
        orderResponse.setOrderStatus(newOrder.getOrderStatus());
        orderResponse.setOrderTime(newOrder.getOrderTime());
        orderResponse.setItems(cartItemsJson); // Set cart items JSON string in the response
        orderResponse.setRestaurant(restaurantResponse);
        orderResponse.setUser(userResponse);
        orderResponse.setAddressId(newOrder.getAddressId()); // Set addressId in response

        return orderResponse;
    }
    @Transactional
    public boolean cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found."));

        // Check if the order can be canceled
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(order.getOrderTime(), now);

        if (duration.getSeconds() <= 30 && order.getOrderStatus() == OrderStatus.PENDING) {
            order.setOrderStatus(OrderStatus.CANCELED);
            orderRepository.save(order);
            return true;
        }

        return false;
    }

}
