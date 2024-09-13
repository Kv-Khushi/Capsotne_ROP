
package com.orders.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.constant.ConstantMessages;
import com.orders.entities.Order;
import com.orders.dto.MessageResponse;
import com.orders.dto.OrderResponse;
import com.orders.service.OrderService;
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
    public ResponseEntity<?> createOrderFromCart(@PathVariable final Long userId,
                                                 @PathVariable final Long addressId) {
        try {
            OrderResponse orderResponse = orderService.createOrderFromCart(userId, addressId);
            System.out.println(" hey "+orderResponse);
            return ResponseEntity.ok(orderResponse);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (JsonProcessingException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing JSON");
        }
    }

    /**
     * Cancels an order based on its ID.
     *
     * @param orderId the ID of the order to cancel
     * @return a response entity with a message indicating the result of the cancellation
     */
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<MessageResponse> cancelOrder(@PathVariable final Long orderId) {
        boolean canceled = orderService.cancelOrder(orderId);
        if (canceled) {
            return ResponseEntity.ok(new MessageResponse(ConstantMessages.ORDER_CANCELED_SUCCESSFULLY));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(ConstantMessages.ORDER_CANNOT_BE_CANCELED));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderResponse> orders = orderService.getOrdersByUserId(userId);
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<Order> getOrdersByRestaurantId(@PathVariable Long restaurantId) {
        return orderService.getOrdersByRestaurantId(restaurantId);
    }

    @PutMapping("/{orderId}/complete")
    public ResponseEntity<MessageResponse> completeOrder(@PathVariable Long orderId) {
        boolean complete = orderService.completeOrder(orderId);
        if (complete) {
            return ResponseEntity.ok(new MessageResponse(ConstantMessages.ORDER_CANCELED_SUCCESSFULLY));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(ConstantMessages.ORDER_CANNOT_BE_CANCELED));
        }
    }
}
