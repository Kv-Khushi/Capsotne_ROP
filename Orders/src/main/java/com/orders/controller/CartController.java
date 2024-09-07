package com.orders.controller;

import com.orders.entities.Cart;
import com.orders.indto.CartRequest;
import com.orders.outdto.CartResponse;
import com.orders.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Add item to the cart
    @PostMapping("/add")
    public ResponseEntity<?> addItemToCart(@RequestBody CartRequest cartRequest) {
        try {
            Cart addedCart = cartService.addItemToCart(cartRequest);
            return ResponseEntity.ok(addedCart);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());  // Return 400 with error message
        }
    }


    @DeleteMapping("/remove/{userId}/{foodItemId}")
    public ResponseEntity<String> removeItemFromCart(@PathVariable("userId") Long userId,
                                                     @PathVariable("foodItemId") Long foodItemId) {
        cartService.removeItemFromCart(userId, foodItemId);
        return ResponseEntity.ok("Item removed from cart successfully.");
    }

    @PutMapping("/updateQuantity")
    public ResponseEntity<String> updateItemQuantity(@RequestBody CartRequest cartRequest) {
        cartService.updateItemQuantity(cartRequest);
        return ResponseEntity.ok("Item quantity updated successfully.");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartResponse>> getAllCartItemsByUserId(@PathVariable Long userId) {
        List<CartResponse> cartItems = cartService.getAllCartItemsByUserId(userId);
        return ResponseEntity.ok(cartItems);
    }


}
