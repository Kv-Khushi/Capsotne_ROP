package com.orders.dtoconversion;

import com.orders.entities.Cart;
import com.orders.entities.Order;
import com.orders.enums.OrderStatus;
import com.orders.indto.OrderRequest;
import com.orders.indto.CartRequest;
import com.orders.outdto.CartResponse;
import com.orders.outdto.RestaurantMenuResponse;
import org.springframework.stereotype.Service;



@Service
public class DtoConversion {



    public Cart cartRequestToCart(CartRequest cartRequest) {
        Cart cart = new Cart();
        cart.setFoodItemId(cartRequest.getFoodItemId());
        cart.setQuantity(cartRequest.getQuantity());
        return cart;
    }

    public CartResponse cartToCartResponse(Cart cart, RestaurantMenuResponse menuResponse) {
        CartResponse cartResponse = new CartResponse();
        cartResponse.setCartId(cart.getCartId());
        cartResponse.setFoodItemId(cart.getFoodItemId());
        cartResponse.setQuantity(cart.getQuantity());
        cartResponse.setPricePerItem(cart.getPricePerItem());

        return cartResponse;
    }

}



