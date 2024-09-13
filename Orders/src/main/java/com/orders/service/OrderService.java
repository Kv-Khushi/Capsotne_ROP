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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Service class for managing orders.
 */
@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

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
    private ObjectMapper objectMapper;

    /**
     * Creates an order from the items in the user's cart.
     *
     * @param userId the ID of the user placing the order
     * @param addressId the ID of the address for delivery
     * @return the created OrderResponse
     * @throws JsonProcessingException if there is an error serializing cart items to JSON
     */
    @Transactional
    public OrderResponse createOrderFromCart(Long userId, Long addressId) throws JsonProcessingException {
        try {
            // Existing code...
            // Fetch the user from the user service
            UserResponse userResponse = userFeignClient.getUserById(userId);
            if (userResponse == null) {
                logger.error("User not found with ID: {}", userId);
                throw new ResourceNotFoundException(ConstantMessages.INVALID_USER_ID);
            }

            // Fetch all cart items for the user
            List<Cart> cartItems = cartRepository.findByUserId(userId);
            if (cartItems.isEmpty()) {
                logger.error("No items in cart for user ID: {}", userId);
                throw new InvalidRequestException(ConstantMessages.NO_ITEMS_IN_CART);
            }

            // Fetch the restaurant details from the restaurant service
            Long restaurantId = cartItems.get(0).getRestaurantId();
            RestaurantResponse restaurantResponse = restaurantFeignClient.getRestaurantById(restaurantId);
            if (restaurantResponse == null) {
                logger.error("Restaurant not found with ID: {}", restaurantId);
                throw new ResourceNotFoundException(ConstantMessages.INVALID_RESTAURANT_ID);
            }

            // Fetch all addresses for the user
            List<AddressResponse> addresses = userFeignClient.getAllAddressesForUser(userId);

            // Validate that the provided addressId belongs to the user
            boolean addressBelongsToUser = addresses.stream()
                    .anyMatch(address -> address.getAddressId().equals(addressId));

            if (!addressBelongsToUser) {
                logger.error("Invalid address ID: {} for user ID: {}", addressId, userId);
                throw new InvalidRequestException(ConstantMessages.INVALID_ADDRESS_ID);
            }

            // Calculate the total price
            double totalPrice = cartItems.stream()
                    .mapToDouble(cart -> cart.getPricePerItem() * cart.getQuantity())
                    .sum();

            // Check if the user has sufficient funds
            if (userResponse.getWallet() < totalPrice) {
                logger.error("Insufficient funds for user ID: {}. Wallet balance: {}, Total price: {}", userId, userResponse.getWallet(), totalPrice);
                throw new InvalidRequestException("Insufficient funds in wallet");
            }

            // Deduct the amount from user's wallet
            userFeignClient.updateWalletBalance(userId, userResponse.getWallet() - totalPrice);

            // Create a new order
            Order newOrder = new Order();
            newOrder.setUserId(userId);
            newOrder.setRestaurantId(restaurantId);
            newOrder.setAddressId(addressId);
            newOrder.setOrderTime(LocalDateTime.now());
            newOrder.setOrderStatus(OrderStatus.PENDING);
            newOrder.setTotalPrice(totalPrice);

            // Convert Cart items to CartResponse DTOs
            List<CartResponse> cartResponses = cartItems.stream()
                    .map(cart -> {
                        RestaurantMenuResponse menuResponse = restaurantFeignClient.getMenuItemById(cart.getFoodItemId());
                        return dtoConversion.cartToCartResponse(cart, menuResponse);
                    })
                    .collect(Collectors.toList());

            // Serialize cart items to JSON string
            String cartItemsJson = objectMapper.writeValueAsString(cartResponses);
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
            orderResponse.setItems(cartItemsJson);

            return orderResponse;
        } catch (Exception e) {
            logger.error("Error creating order for user ID: {} with address ID: {}", userId, addressId, e);
            throw new RuntimeException("Failed to create order", e);
        }
    }

    /**
     * Cancels an order if it is still pending and within the allowed cancellation time.
     *
     * @param orderId the ID of the order to cancel
     * @return true if the order was successfully canceled, false otherwise
     */
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

            UserResponse userResponse = userFeignClient.getUserById(order.getUserId());
            double currentWalletBalance = userResponse.getWallet();
            double orderTotalPrice = order.getTotalPrice();

            userFeignClient.updateWalletBalance(order.getUserId(), currentWalletBalance + orderTotalPrice);
            return true;
        }

        return false;
    }



    public List<OrderResponse> getOrdersByUserId(Long userId) {
        // Fetch the order list from the repository
        List<Order> orderList = orderRepository.findByUserId(userId);

        if (orderList.isEmpty()) {
            throw new ResourceNotFoundException(ConstantMessages.NO_ORDERS_FOUND);
        }

        // Convert orderList to orderResponseList
        List<OrderResponse> orderResponseList = orderList.stream()
                .map(this::convertToOrderResponse)
                .collect(Collectors.toList());

        return orderResponseList;
    }

    // Method to convert Order to OrderResponse
    private OrderResponse convertToOrderResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(order.getOrderId());
        orderResponse.setTotalPrice(order.getTotalPrice());
        orderResponse.setOrderStatus(order.getOrderStatus());
        orderResponse.setOrderTime(order.getOrderTime());
        orderResponse.setItems(order.getItems()); // This is a JSON string, you can handle it as needed
        return orderResponse;
    }


    public List<Order> getOrdersByRestaurantId(Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }



    public boolean completeOrder(Long orderId) {
        // Fetch the order by ID
        Order order = orderRepository.findById(orderId).orElse(null);

        // Check if the order exists
        if (order == null) {
            return false; // Order not found
        }

        // Update the order status to COMPLETED
        order.setOrderStatus(OrderStatus.COMPLETED);

        // Save the updated order
        orderRepository.save(order);

        return true; // Order successfully completed
    }
}