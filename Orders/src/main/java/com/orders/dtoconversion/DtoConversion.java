package com.orders.dtoconversion;

import com.orders.entities.Cart;
import com.orders.dto.CartRequest;
import com.orders.dto.CartResponse;
import com.orders.dto.RestaurantMenuResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Service class for converting between DTOs and entities.
 */
@Service
public class DtoConversion {

    /**
     * Converts a {@link CartRequest} to a {@link Cart} entity.
     *
     * @param cartRequest the cart request DTO to convert
     * @return the corresponding {@link Cart} entity
     */
    public Cart cartRequestToCart(@RequestBody final CartRequest cartRequest) {
        Cart cart = new Cart();
        cart.setFoodItemId(cartRequest.getFoodItemId());
        cart.setQuantity(cartRequest.getQuantity());
        return cart;
    }

    /**
     * Converts a {@link Cart} entity and {@link RestaurantMenuResponse} to a {@link CartResponse} DTO.
     *
     * @param cart the {@link Cart} entity to convert
     * @param menuResponse the {@link RestaurantMenuResponse} used for additional data
     * @return the corresponding {@link CartResponse} DTO
     */
    public CartResponse cartToCartResponse(@RequestBody final Cart cart,
                                           @RequestBody final RestaurantMenuResponse menuResponse) {
        CartResponse cartResponse = new CartResponse();
        cartResponse.setCartId(cart.getCartId());
        cartResponse.setFoodItemId(cart.getFoodItemId());
        cartResponse.setQuantity(cart.getQuantity());
        cartResponse.setPricePerItem(cart.getPricePerItem());
        return cartResponse;
    }
}
