package com.orders.controller;

import com.orders.outdto.OrderResponse;
import com.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create/{userId}/{addressId}")
    public ResponseEntity<?> createOrderFromCart(@PathVariable Long userId, @PathVariable Long addressId) {
        try {
            OrderResponse orderResponse = orderService.createOrderFromCart(userId, addressId);
            return ResponseEntity.ok(orderResponse);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        boolean canceled = orderService.cancelOrder(orderId);
        if (canceled) {
            return ResponseEntity.ok("Order canceled successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order cannot be canceled.");
        }
    }
}
