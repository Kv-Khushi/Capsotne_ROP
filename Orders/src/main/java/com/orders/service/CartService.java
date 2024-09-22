
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;



@Service
@Slf4j
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private DtoConversion dtoConversion;

    @Autowired
    private RestaurantFeignClient restaurantFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;


    /**
     * Adds an item to the cart. If the item already exists in the cart, it updates the quantity.
     * It also validates user, restaurant, and food item existence.
     *
     * @param cartRequest the details of the cart item to add
     * @return the updated or newly created {@link Cart} entity
     * @throws ResourceNotFoundException if user, restaurant, or food item is not found
     * @throws InvalidRequestException   if multiple restaurants are found in the cart
     */
    @Transactional
    public Cart addItemToCart(final CartRequest cartRequest) {
        log.info("Received request to add item to cart: {}", cartRequest);

        RestaurantMenuResponse menuResponse = null;

        try {

            UserResponse userResponse = userFeignClient.getUserById(cartRequest.getUserId());
            if (userResponse == null) {
                throw new ResourceNotFoundException(ConstantMessages.INVALID_USER_ID);
            }
        } catch (FeignException ex) {
            throw new RuntimeException(ConstantMessages.USER_SERVICE_DOWN);
        }

        try {
            RestaurantResponse restaurantResponse = restaurantFeignClient.getRestaurantById(cartRequest.getRestaurantId());
            if (restaurantResponse == null) {
                throw new ResourceNotFoundException(ConstantMessages.INVALID_RESTAURANT_ID);
            }
        } catch (FeignException ex) {
            throw new RuntimeException(ConstantMessages.RESTAURANT_SERVICE_DOWN);
        }

        try {
            menuResponse = restaurantFeignClient.getMenuItemById(cartRequest.getFoodItemId());
            if (menuResponse == null) {
                throw new ResourceNotFoundException(ConstantMessages.INVALID_FOOD_ITEM_ID);
            }
        } catch (FeignException ex) {
            throw new RuntimeException(ConstantMessages.RESTAURANT_SERVICE_DOWN);
        }

        List<Cart> existingCartItems = cartRepository.findByUserId(cartRequest.getUserId());

        if (!existingCartItems.isEmpty()) {
            Long existingRestaurantId = existingCartItems.get(0).getRestaurantId();
            if (!existingRestaurantId.equals(cartRequest.getRestaurantId())) {
                log.error("Cart contains items from multiple restaurants. Request for restaurant ID {}", cartRequest.getRestaurantId());
                throw new InvalidRequestException(ConstantMessages.MULTIPLE_RESTAURANT_ERROR);
            }
        }

        RestaurantMenuResponse finalMenuResponse = menuResponse;

        return cartRepository.findByUserIdAndFoodItemId(cartRequest.getUserId(), cartRequest.getFoodItemId())
                .map(cart -> {
                    cart.setQuantity(cart.getQuantity() + cartRequest.getQuantity());
                    return cartRepository.save(cart);
                })
                .orElseGet(() -> {
                    Cart newCart = dtoConversion.cartRequestToCart(cartRequest);
                    newCart.setUserId(cartRequest.getUserId());
                    newCart.setRestaurantId(cartRequest.getRestaurantId());
                    newCart.setPricePerItem(finalMenuResponse.getPrice());
                    return cartRepository.save(newCart);
                });
    }

    /**
     * Removes an item from the cart by user ID and food item ID.
     *
     * @param userId     the ID of the user
     * @param foodItemId the ID of the food item to remove
     * @throws ResourceNotFoundException if the item is not found in the cart
     */
    public void removeItemFromCart(final Long userId, final Long foodItemId) {
        log.info("Received request to remove item from cart for user ID {} and food item ID {}", userId, foodItemId);
        Cart cart = cartRepository.findByUserIdAndFoodItemId(userId, foodItemId)
                .orElseThrow(() -> new ResourceNotFoundException(ConstantMessages.ITEM_NOT_FOUND));

        cartRepository.delete(cart);
    }


    /**
     * Retrieves all cart items for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of {@link CartResponse} DTOs representing the cart items
     */
    public List<CartResponse> getAllCartItemsByUserId(final Long userId) {
        log.info("Received request to retrieve all cart items for user ID {}", userId);
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        List<CartResponse> cartResponseList = new ArrayList<>();
        for (Cart cartItem : cartItems) {
            DtoConversion dtoConversion= new DtoConversion();
         cartResponseList.add(dtoConversion.cartToCartResponse(cartItem));
        }
        return cartResponseList;
    }
}

