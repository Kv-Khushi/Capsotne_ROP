package com.orders.outdto;


import lombok.Data;

@Data
public class CartResponse {
    private Long cartId;
//    private long restaurantId;
//    private Long orderItemId;
    private Long foodItemId;
    private Integer quantity;
    private Double pricePerItem;

}
