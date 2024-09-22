package com.orders.service;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;




/**
 * Service class for managing orders.
 */
@Service
@Slf4j
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
    private ObjectMapper objectMapper;

    /**
     * Creates an order from the items in the user's cart.
     *
     * @param userId    the ID of the user placing the order
     * @param addressId the ID of the address for delivery
     * @return the created OrderResponse
     */
    @Transactional
    public OrderResponse createOrderFromCart(final Long userId, final Long addressId) {
        try {

            UserResponse userResponse = userFeignClient.getUserById(userId);
            if (userResponse == null) {
                log.error("User not found with ID: {}", userId);
                throw new ResourceNotFoundException(ConstantMessages.INVALID_USER_ID);
            }


            List<Cart> cartItems = cartRepository.findByUserId(userId);
            if (cartItems.isEmpty()) {
                log.error("No items in cart for user ID: {}", userId);
                throw new InvalidRequestException(ConstantMessages.NO_ITEMS_IN_CART);
            }

            Long restaurantId = cartItems.get(0).getRestaurantId();
            RestaurantResponse restaurantResponse = restaurantFeignClient.getRestaurantById(restaurantId);
            if (restaurantResponse == null) {
                log.error("Restaurant not found with ID: {}", restaurantId);
                throw new ResourceNotFoundException(ConstantMessages.INVALID_RESTAURANT_ID);
            }


            List<AddressResponse> addresses = userFeignClient.getAllAddressesForUser(userId);


            boolean addressBelongsToUser = addresses.stream()
                    .anyMatch(address -> address.getAddressId().equals(addressId));

            if (!addressBelongsToUser) {
                log.error("Invalid address ID: {} for user ID: {}", addressId, userId);
                throw new InvalidRequestException(ConstantMessages.INVALID_ADDRESS_ID);
            }


            double totalPrice = cartItems.stream()
                    .mapToDouble(cart -> cart.getPricePerItem() * cart.getQuantity())
                    .sum();


            if (userResponse.getWallet() < totalPrice) {
                log.error("Insufficient funds for user ID: {}. Wallet balance: {}, Total price: {}", userId, userResponse.getWallet(), totalPrice);
                throw new InvalidRequestException(ConstantMessages.INSUFFICIENT_AMOUNT);
            }

            userFeignClient.updateWalletBalance(userId, userResponse.getWallet() - totalPrice);


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

                        DtoConversion dtoConversion= new DtoConversion();
                        return dtoConversion.cartToCartResponse(cart);
                    })
                    .collect(Collectors.toList());

            // Serialize cart items to JSON string
            String cartItemsJson = objectMapper.writeValueAsString(cartResponses);
            newOrder.setItems(cartItemsJson);

            // Save the order in the database
             Order savedOrder = orderRepository.save(newOrder);

            // Remove all cart items after creating the order
            cartRepository.deleteAll(cartItems);

            // Create OrderResponse DTO
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setOrderId(savedOrder.getOrderId());
            orderResponse.setTotalPrice(totalPrice);
            orderResponse.setOrderStatus(savedOrder.getOrderStatus());
            orderResponse.setOrderTime(savedOrder.getOrderTime());
            orderResponse.setItems(cartItemsJson);

            return orderResponse;
        } catch (Exception e) {
            log.error("Error creating order for user ID: {} with address ID: {}", userId, addressId, e);
            throw new RuntimeException("Failed to create order", e);
        }
    }
    /**
     * Cancels an order if it is still pending and within the allowed cancellation time.
     * <p>
     * This method can be overridden by subclasses to provide additional cancellation
     * logic, but subclasses should ensure that they maintain the constraints such as
     * checking the order status and the allowed cancellation time. Subclasses are
     * encouraged to call {@code super.cancelOrder(orderId)} to reuse the existing logic.
     * </p>
     *
     * @param orderId the ID of the order to cancel
     * @return true if the order was successfully canceled, false otherwise
     */
    @Transactional
    public  boolean cancelOrder(final Long orderId) {
        log.info("Received request to cancel order with ID: {}", orderId);
        Order order = orderRepository.findById(orderId)
         .orElseThrow(() -> new ResourceNotFoundException(ConstantMessages.NO_ORDERS_FOUND));

        // Check if the order can be canceled
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(order.getOrderTime(), now);

        if (duration.getSeconds() <= 30 && order.getOrderStatus() == OrderStatus.PENDING) {
            order.setOrderStatus(OrderStatus.CANCELED);
            orderRepository.save(order);
            log.info("Order with ID: {} has been canceled", orderId);

            UserResponse userResponse = userFeignClient.getUserById(order.getUserId());
            double currentWalletBalance = userResponse.getWallet();
            double orderTotalPrice = order.getTotalPrice();

            userFeignClient.updateWalletBalance(order.getUserId(), currentWalletBalance + orderTotalPrice);
            return true;
        }
        return false;
    }


    /**
     * Retrieves all orders for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of OrderResponse representing the orders
     */
    public List<OrderResponse> getOrdersByUserId(final Long userId) {
        log.info("Received request to retrieve orders for user ID: {}", userId);
        List<Order> orderList = orderRepository.findByUserId(userId);
        if (orderList.isEmpty()) {
            throw new ResourceNotFoundException(ConstantMessages.NO_ORDERS_FOUND);
        }
        List<OrderResponse> orderResponseList = orderList.stream()
                .map(this::convertToOrderResponse)
                .collect(Collectors.toList());
        log.info("Retrieved {} orders for user ID: {}", orderResponseList.size(), userId);
        return orderResponseList;
    }

    /**
     * Converts an Order entity to an OrderResponse DTO.
     *
     * @param order the Order entity
     * @return the OrderResponse DTO
     */
    private OrderResponse convertToOrderResponse(final Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(order.getOrderId());
        orderResponse.setTotalPrice(order.getTotalPrice());
        orderResponse.setOrderStatus(order.getOrderStatus());
        orderResponse.setOrderTime(order.getOrderTime());
        orderResponse.setItems(order.getItems()); // This is a JSON string, you can handle it as needed
        return orderResponse;
    }

    /**
     * Retrieves all orders for a specific restaurant.
     *
     * @param restaurantId the ID of the restaurant
     * @return a list of Order entities
     */
    public List<Order> getOrdersByRestaurantId(final Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }


    /**
     * Completes an order by updating its status to COMPLETED.
     *
     * @param orderId the ID of the order to complete
     * @return true if the order was successfully completed, false otherwise
     */
    public boolean completeOrder(final Long orderId) {
       Optional<Order> order = orderRepository.findById(orderId);
        if (!order.isPresent()) {
            return false;
        }
        order.get().setOrderStatus(OrderStatus.COMPLETED);
        orderRepository.save(order.get());
        return true;
    }
}
