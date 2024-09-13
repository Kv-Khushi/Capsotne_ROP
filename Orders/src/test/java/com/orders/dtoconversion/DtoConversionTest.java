package com.orders.dtoconversion;

import com.orders.entities.Cart;
import com.orders.dto.CartRequest;
import com.orders.dto.CartResponse;
import com.orders.dto.RestaurantMenuResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class DtoConversionTest {

    @InjectMocks
    private DtoConversion dtoConversion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Test cartRequestToCart method
    @Test
    public void testCartRequestToCart() {
        // Arrange
        CartRequest cartRequest = new CartRequest();
        cartRequest.setFoodItemId(1L);
        cartRequest.setQuantity(5);

        // Act
        Cart cart = dtoConversion.cartRequestToCart(cartRequest);

        // Assert
        assertNotNull(cart);
        assertEquals(cartRequest.getFoodItemId(), cart.getFoodItemId());
        assertEquals(cartRequest.getQuantity(), cart.getQuantity());
    }

    // Test cartToCartResponse method
    @Test
    public void testCartToCartResponse() {
        // Arrange
        Cart cart = new Cart();
        cart.setCartId(1L);
        cart.setFoodItemId(2L);
        cart.setQuantity(3);
        cart.setPricePerItem(10.0);

        RestaurantMenuResponse menuResponse = new RestaurantMenuResponse();

        // Act
        CartResponse cartResponse = dtoConversion.cartToCartResponse(cart, menuResponse);

        // Assert
        assertNotNull(cartResponse);
        assertEquals(cart.getCartId(), cartResponse.getCartId());
        assertEquals(cart.getFoodItemId(), cartResponse.getFoodItemId());
        assertEquals(cart.getQuantity(), cartResponse.getQuantity());
        assertEquals(cart.getPricePerItem(), cartResponse.getPricePerItem());
    }
}
