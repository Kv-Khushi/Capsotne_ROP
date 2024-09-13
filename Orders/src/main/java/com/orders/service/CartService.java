
package com.orders.service;
import com.orders.constant.ConstantMessages;
import com.orders.dtoconversion.DtoConversion;
import com.orders.entities.Cart;
import com.orders.exception.InvalidRequestException;
import com.orders.exception.ResourceNotFoundException;
import com.orders.feignclientconfig.RestaurantFeignClient;
import com.orders.feignclientconfig.UserFeignClient;
import com.orders.dto.CartRequest;
import com.orders.dto.CartResponse;
import com.orders.dto.RestaurantMenuResponse;
import com.orders.dto.RestaurantResponse;
import com.orders.dto.UserResponse;
import com.orders.repository.CartRepository;
import feign.FeignException;
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


    /**
     * Adds an item to the cart. If the item already exists in the cart, it updates the quantity.
     * It also validates user, restaurant, and food item existence.
     *
     * @param cartRequest the details of the cart item to add
     * @return the updated or newly created {@link Cart} entity
     * @throws ResourceNotFoundException if user, restaurant, or food item is not found
     * @throws InvalidRequestException if multiple restaurants are found in the cart
     */
    @Transactional
    public Cart addItemToCart(CartRequest cartRequest) {
        RestaurantMenuResponse menuResponse = null;

        try {
            // Validate user existence
            UserResponse userResponse = userFeignClient.getUserById(cartRequest.getUserId());
            if (userResponse == null) {
                throw new ResourceNotFoundException(ConstantMessages.INVALID_USER_ID);
            }
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException(ConstantMessages.INVALID_USER_ID);
        }

        try {
            // Validate restaurant existence
            RestaurantResponse restaurantResponse = restaurantFeignClient.getRestaurantById(cartRequest.getRestaurantId());
            if (restaurantResponse == null) {
                throw new ResourceNotFoundException(ConstantMessages.INVALID_RESTAURANT_ID);
            }
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException(ConstantMessages.INVALID_RESTAURANT_ID);
        }

        try {
            // Fetch food item details
            menuResponse = restaurantFeignClient.getMenuItemById(cartRequest.getFoodItemId());
            if (menuResponse == null) {
                throw new ResourceNotFoundException(ConstantMessages.INVALID_FOOD_ITEM_ID);
            }
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException(ConstantMessages.INVALID_FOOD_ITEM_ID);
        }

        List<Cart> existingCartItems = cartRepository.findByUserId(cartRequest.getUserId());

        if (!existingCartItems.isEmpty()) {
            Long existingRestaurantId = existingCartItems.get(0).getRestaurantId();
            if (!existingRestaurantId.equals(cartRequest.getRestaurantId())) {
                throw new InvalidRequestException(ConstantMessages.MULTIPLE_RESTAURANT_ERROR);
            }
        }

        RestaurantMenuResponse finalMenuResponse = menuResponse;
        RestaurantMenuResponse finalMenuResponse1 = menuResponse;
        return cartRepository.findByUserIdAndFoodItemId(cartRequest.getUserId(), cartRequest.getFoodItemId())
                .map(cart -> {
                    cart.setQuantity(cart.getQuantity() + cartRequest.getQuantity());
                    cart.setPricePerItem(finalMenuResponse.getPrice());
                    return cartRepository.save(cart);
                })
                .orElseGet(() -> {
                    Cart newCart = dtoConversion.cartRequestToCart(cartRequest);
                    newCart.setUserId(cartRequest.getUserId());
                    newCart.setRestaurantId(cartRequest.getRestaurantId());
                    newCart.setPricePerItem(finalMenuResponse1.getPrice());
                    return cartRepository.save(newCart);
                });
    }

    /**
     * Removes an item from the cart by user ID and food item ID.
     *
     * @param userId the ID of the user
     * @param foodItemId the ID of the food item to remove
     * @throws ResourceNotFoundException if the item is not found in the cart
     */
    @Transactional
    public void removeItemFromCart(Long userId, Long foodItemId) {
        Cart cart = cartRepository.findByUserIdAndFoodItemId(userId, foodItemId)
                .orElseThrow(() -> new ResourceNotFoundException(ConstantMessages.ITEM_NOT_FOUND));

        cartRepository.delete(cart);
    }



    /**
     * Updates the quantity of an item in the cart and adjusts the price accordingly.
     *
     * @param cartRequest the updated details of the cart item
     * @throws IllegalArgumentException if the item is not found or if the quantity is negative
     */
    @Transactional
    public void updateItemQuantity(CartRequest cartRequest) {
        logger.info("Received cart update request: {}", cartRequest);
        // Find the cart item by userId and foodItemId
        Cart cart = cartRepository.findByUserIdAndFoodItemId(cartRequest.getUserId(), cartRequest.getFoodItemId())
                .orElseThrow(() -> new IllegalArgumentException(ConstantMessages.ITEM_NOT_FOUND));

        // Validate quantity
        int newQuantity = cartRequest.getQuantity();
        if (newQuantity < 0) {
            throw new IllegalArgumentException(ConstantMessages.NEGATIVE_QUANTITY_ERROR);
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


    /**
     * Retrieves all cart items for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of {@link CartResponse} DTOs representing the cart items
     */
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

