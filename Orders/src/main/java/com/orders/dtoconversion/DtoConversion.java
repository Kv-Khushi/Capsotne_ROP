package com.orders.dtoconversion;


import com.orders.entities.Cart;
import com.orders.dto.CartRequest;
import com.orders.dto.CartResponse;
import org.springframework.stereotype.Service;



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
    public  Cart cartRequestToCart(final CartRequest cartRequest) {
        Cart cart = new Cart();
        cart.setFoodItemId(cartRequest.getFoodItemId());
        cart.setQuantity(cartRequest.getQuantity());

        return cart;
    }

    /**
     *
     * @param cart the {@link Cart} entity to convert
     * @return the corresponding {@link CartResponse} DTO
     */
    public  CartResponse cartToCartResponse(final Cart cart) {
        CartResponse cartResponse = new CartResponse();
        cartResponse.setCartId(cart.getCartId());
        cartResponse.setFoodItemId(cart.getFoodItemId());
        cartResponse.setQuantity(cart.getQuantity());
        cartResponse.setPricePerItem(cart.getPricePerItem());
        return cartResponse;
    }

}
