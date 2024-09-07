package com.orders.indto;


import lombok.Data;

@Data
public class CartRequest {
    private Long userId;
    private Long restaurantId;
    private Long cartId;
    private Integer quantity;
    private Double pricePerItem;
    private Long foodItemId;

}
