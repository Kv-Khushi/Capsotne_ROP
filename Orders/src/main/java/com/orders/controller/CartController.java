
package com.orders.controller;

import com.orders.constant.ConstantMessages;
import com.orders.entities.Cart;
import com.orders.dto.CartRequest;
import com.orders.dto.CartResponse;
import com.orders.dto.MessageResponse;
import com.orders.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling cart-related operations.
 */
@RestController
@RequestMapping("/cart")
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
    public ResponseEntity<?> addItemToCart(@RequestBody final CartRequest cartRequest) {
        Cart addedCart = cartService.addItemToCart(cartRequest);
        return ResponseEntity.ok(addedCart);
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
        cartService.removeItemFromCart(userId, foodItemId);
        return ResponseEntity.ok(new MessageResponse(ConstantMessages.ITEM_REMOVED_SUCCESSFULLY));
    }

    /**
     * Updates the quantity of an item in the cart.
     *
     * @param cartRequest the request body containing details of the item and the new quantity
     * @return a response entity with a message indicating the result of the operation
     */
    @PutMapping("/updateQuantity")
    public ResponseEntity<MessageResponse> updateItemQuantity(@RequestBody final CartRequest cartRequest) {
        cartService.updateItemQuantity(cartRequest);
        return ResponseEntity.ok(new MessageResponse(ConstantMessages.ITEM_QUANTITY_UPDATED_SUCCESSFULLY));
    }

    /**
     * Retrieves all cart items for a specific user.
     *
     * @param userId the ID of the user
     * @return a response entity containing a list of cart items for the user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartResponse>> getAllCartItemsByUserId(@PathVariable final Long userId) {
        List<CartResponse> cartItems = cartService.getAllCartItemsByUserId(userId);
        return ResponseEntity.ok(cartItems);
    }
}

