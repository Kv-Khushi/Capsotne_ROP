package com.orders.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.orders.constant.ConstantMessages;
import com.orders.outdto.MessageResponse;
import com.orders.outdto.OrderResponse;
import com.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;


    @PostMapping("/create/{userId}/{addressId}")
    public ResponseEntity<?> createOrderFromCart(@PathVariable Long userId, @PathVariable Long addressId) {
        try {
            OrderResponse orderResponse = orderService.createOrderFromCart(userId, addressId);
            return ResponseEntity.ok(orderResponse);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (JsonProcessingException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing JSON");
        }
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<MessageResponse> cancelOrder(@PathVariable Long orderId) {
        boolean canceled = orderService.cancelOrder(orderId);
        if (canceled) {
            return ResponseEntity.ok(new MessageResponse(ConstantMessages.ORDER_CANCELED_SUCCESSFULLY));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(ConstantMessages.ORDER_CANNOT_BE_CANCELED));
        }
    }

}
