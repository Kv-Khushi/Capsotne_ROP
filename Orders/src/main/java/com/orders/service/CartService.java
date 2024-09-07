package com.orders.service;

import com.orders.entities.Cart;
import com.orders.dtoconversion.DtoConversion;
import com.orders.feignclientconfig.RestaurantFeignClient;
import com.orders.feignclientconfig.UserFeignClient;
import com.orders.indto.CartRequest;

import com.orders.outdto.CartResponse;
import com.orders.outdto.RestaurantMenuResponse;
import com.orders.outdto.RestaurantResponse;
import com.orders.outdto.UserResponse;
import com.orders.repository.CartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CartService {

        @Autowired
        private CartRepository cartRepository;

        @Autowired
        private DtoConversion dtoConversion;

        @Autowired
        private RestaurantFeignClient restaurantFeignClient;

       @Autowired
       private UserFeignClient userFeignClient;

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    @Transactional
    public Cart addItemToCart(CartRequest cartRequest) {
        // Validate user existence
        UserResponse userResponse = userFeignClient.getUserById(cartRequest.getUserId());
        if (userResponse == null) {
            throw new IllegalArgumentException("Invalid user ID.");
        }

        // Validate restaurant existence
        RestaurantResponse restaurantResponse = restaurantFeignClient.getRestaurantById(cartRequest.getRestaurantId());
        if (restaurantResponse == null) {
            throw new IllegalArgumentException("Invalid restaurant ID.");
        }

        // Fetch food item details
        RestaurantMenuResponse menuResponse = restaurantFeignClient.getMenuItemById(cartRequest.getFoodItemId());
        if (menuResponse == null) {
            throw new IllegalArgumentException("Invalid food item ID.");
        }

        // Fetch all cart items for the given user
        List<Cart> existingCartItems = cartRepository.findByUserId(cartRequest.getUserId());

        // Check if the cart already contains items from a different restaurant
        if (!existingCartItems.isEmpty()) {
            Long existingRestaurantId = existingCartItems.get(0).getRestaurantId();
            if (!existingRestaurantId.equals(cartRequest.getRestaurantId())) {
                throw new IllegalArgumentException("You can only add items from one restaurant at a time.");
            }
        }

        // Check if the item is already in the cart
        return cartRepository.findByUserIdAndFoodItemId(cartRequest.getUserId(), cartRequest.getFoodItemId())
                .map(cart -> {
                    // Update existing cart item
                    cart.setQuantity(cart.getQuantity() + cartRequest.getQuantity()); // Update quantity
                    // Update price based on food item details from restaurant service
                    cart.setPricePerItem(menuResponse.getPrice());
                    return cartRepository.save(cart);
                })
                .orElseGet(() -> {
                    // Use DtoConversion to convert CartRequest to Cart for new cart item
                    Cart newCart = dtoConversion.cartRequestToCart(cartRequest);
                    newCart.setUserId(cartRequest.getUserId());  // Add user ID
                    newCart.setRestaurantId(cartRequest.getRestaurantId());  // Add restaurant ID
                    newCart.setPricePerItem(menuResponse.getPrice()); // Set price based on food item details
                    return cartRepository.save(newCart);

                });
    }


    @Transactional
    public void removeItemFromCart(Long userId, Long foodItemId) {
        // Find the cart item by userId and foodItemId
        Cart cart = cartRepository.findByUserIdAndFoodItemId(userId, foodItemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found in cart."));

        // Remove the item from the cart
        cartRepository.delete(cart);
    }


    @Transactional
    public void updateItemQuantity(CartRequest cartRequest) {
        logger.info("Received cart update request: {}", cartRequest);
        // Find the cart item by userId and foodItemId
        Cart cart = cartRepository.findByUserIdAndFoodItemId(cartRequest.getUserId(), cartRequest.getFoodItemId())
                .orElseThrow(() -> new IllegalArgumentException("Item not found in cart."));

        // Validate quantity
        int newQuantity = cartRequest.getQuantity();
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }

        // Fetch the latest price from the restaurant service
        RestaurantMenuResponse menuResponse = restaurantFeignClient.getMenuItemById(cartRequest.getFoodItemId());
        double latestPrice = menuResponse.getPrice();
        logger.info("Fetched latest price for food item {}: {}", cartRequest.getFoodItemId(), latestPrice);

        // Update the cart item
        cart.setQuantity(newQuantity);
        cart.setPricePerItem(latestPrice * newQuantity); // Update price based on quantity
        cartRepository.save(cart);
        logger.info("Updated cart item: {}", cart);
    }

    public List<CartResponse> getAllCartItemsByUserId(Long userId) {
        // Fetch all cart items by userId
        List<Cart> cartItems = cartRepository.findByUserId(userId);

        // Convert the list of Cart entities to CartResponse DTOs
        return cartItems.stream()
                .map(cart -> {
                    // Here, you can fetch additional details, like menu item information, if needed
                    RestaurantMenuResponse menuResponse = restaurantFeignClient.getMenuItemById(cart.getFoodItemId());
                    return dtoConversion.cartToCartResponse(cart, menuResponse);
                })
                .collect(Collectors.toList());
    }
    }




