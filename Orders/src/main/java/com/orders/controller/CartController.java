
package com.orders.controller;

import com.orders.constant.ConstantMessages;
import com.orders.entities.Cart;
import com.orders.dto.CartRequest;
import com.orders.dto.CartResponse;
import com.orders.dto.MessageResponse;
import com.orders.service.CartService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * Controller for handling cart-related operations.
 */
@RestController
@RequestMapping("/cart")
@Slf4j
public class CartController {


    @Autowired
    private CartService cartService;

    /**
     * Adds an item to the cart.
     *
     * @param cartRequest the request body containing details of the item to add
     * @return a response entity containing the added cart item
     */
    @PostMapping("/add")
    public ResponseEntity<Cart> addItemToCart(@RequestBody final CartRequest cartRequest) {
        log.info("Received request to add item to cart: {}", cartRequest);
        Cart addedCart = cartService.addItemToCart(cartRequest);
        log.info("Successfully added item to cart: {}", addedCart);
        return new ResponseEntity<>(addedCart, HttpStatus.CREATED);
    }

    /**
     * Removes an item from the cart based on user ID and food item ID.
     *
     * @param userId the ID of the user
     * @param foodItemId the ID of the food item to remove
     * @return a response entity with a message indicating the result of the operation
     */
    @DeleteMapping("/remove/{userId}/{foodItemId}")
    public ResponseEntity<MessageResponse> removeItemFromCart(@PathVariable("userId") final Long userId,
                                                              @PathVariable("foodItemId") final Long foodItemId) {
        log.info("Received request to remove item with ID {} for user ID {}", foodItemId, userId);
        cartService.removeItemFromCart(userId, foodItemId);
        log.info("Successfully removed item with ID {} from user ID {}", foodItemId, userId);
        return ResponseEntity.ok(new MessageResponse(ConstantMessages.ITEM_REMOVED_SUCCESSFULLY));
    }


    /**
     * Retrieves all cart items for a specific user.
     *
     * @param userId the ID of the user
     * @return a response entity containing a list of cart items for the user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartResponse>> getAllCartItemsByUserId(@PathVariable final Long userId) {
        log.info("Received request to get all cart items for user ID {}", userId);
        List<CartResponse> cartItems = cartService.getAllCartItemsByUserId(userId);
        log.info("Successfully retrieved {} cart items for user ID {}", cartItems.size(), userId);
        return ResponseEntity.ok(cartItems);
    }

}

