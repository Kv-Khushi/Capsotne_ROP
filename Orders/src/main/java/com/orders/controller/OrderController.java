package com.orders.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.outdto.CartResponse;
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

    @Autowired
    private ObjectMapper objectMapper;



//    @PostMapping("/create/{userId}/{addressId}")
//    public ResponseEntity<?> createOrderFromCart(@PathVariable Long userId, @PathVariable Long addressId) {
//        try {
//            OrderResponse orderResponse = orderService.createOrderFromCart(userId, addressId);
//            return ResponseEntity.ok(orderResponse);
//        } catch (IllegalArgumentException ex) {
//            return ResponseEntity.badRequest().body(ex.getMessage());
//        }
//    }


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
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        boolean canceled = orderService.cancelOrder(orderId);
        if (canceled) {
            return ResponseEntity.ok("Order canceled successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order cannot be canceled.");
        }
    }
}
