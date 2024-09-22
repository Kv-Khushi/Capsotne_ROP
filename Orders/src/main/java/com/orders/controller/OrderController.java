
package com.orders.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.constant.ConstantMessages;
import com.orders.entities.Order;
import com.orders.dto.MessageResponse;
import com.orders.dto.OrderResponse;
import com.orders.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling order-related operations.
 */
@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {



    @Autowired
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Creates an order from the cart for a specific user and address.
     *
     * @param userId the ID of the user
     * @param addressId the ID of the address for the order
     * @return a response entity containing the order response or an error message
     */
    @PostMapping("/create/{userId}/{addressId}")
    public ResponseEntity<OrderResponse> createOrderFromCart(@PathVariable final Long userId,
                                                 @PathVariable final Long addressId)  {
        log.info("Received request to create order for user ID {} and address ID {}", userId, addressId);
        OrderResponse orderResponse = orderService.createOrderFromCart(userId, addressId);
            return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

    /**
     * Cancels an order based on its ID.
     *
     * @param orderId the ID of the order to cancel
     * @return a response entity with a message indicating the result of the cancellation
     */
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<MessageResponse> cancelOrder(@PathVariable final Long orderId) {
        log.info("Received request to cancel order with ID {}", orderId);
        boolean canceled = orderService.cancelOrder(orderId);
        if (canceled) {
            log.info("Order with ID {} canceled successfully", orderId);
            return ResponseEntity.ok(new MessageResponse(ConstantMessages.ORDER_CANCELED_SUCCESSFULLY));
        } else {
            log.error("Order with ID {} cannot be canceled", orderId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(ConstantMessages.ORDER_CANNOT_BE_CANCELED));
        }
    }

    /**
     * Retrieves all orders for a given user ID.
     *
     * @param userId the ID of the user to get orders for
     * @return a response entity containing a list of order responses or a no-content status if no orders are found
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId(@PathVariable final Long userId) {
        log.info("Received request to retrieve orders for user ID {}", userId);
        List<OrderResponse> orders = orderService.getOrdersByUserId(userId);
        log.info("Successfully retrieved {} orders for user ID {}", orders.size(), userId);
        return ResponseEntity.ok(orders);
    }


    /**
     * Retrieves all orders for a given restaurant ID.
     *
     * @param restaurantId the ID of the restaurant to get orders for
     * @return a list of orders for the given restaurant ID
     */
    @GetMapping("/restaurant/{restaurantId}")
    public List<Order> getOrdersByRestaurantId(@PathVariable final Long restaurantId) {
        return orderService.getOrdersByRestaurantId(restaurantId);
    }

    /**
     * Marks an order as complete based on its ID.
     *
     * @param orderId the ID of the order to mark as complete
     * @return a response entity with a message indicating the result of the operation
     */
    @PutMapping("/{orderId}/complete")
    public ResponseEntity<MessageResponse> completeOrder(@PathVariable final Long orderId) {
        log.info("Received request to complete order with ID {}", orderId);
        boolean complete = orderService.completeOrder(orderId);
        if (complete) {
            log.info("Order with ID {} marked as complete successfully", orderId);
            return ResponseEntity.ok(new MessageResponse(ConstantMessages.ORDER_COMPLETED_SUCCESSFULLY));
        } else {
            log.error("Order with ID {} cannot be completed", orderId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(ConstantMessages.ORDER_CANNOT_BE_CANCELED));
        }
    }
}
