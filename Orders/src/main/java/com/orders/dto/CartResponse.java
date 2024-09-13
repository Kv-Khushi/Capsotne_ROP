package com.orders.dto;


import lombok.Data;

@Data
public class CartResponse {
    private Long cartId;
    private Long foodItemId;
    private Integer quantity;
    private Double pricePerItem;

}
