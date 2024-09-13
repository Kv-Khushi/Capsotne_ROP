package com.orders.dto;

import com.orders.entities.Cart;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private Long userId;
    private Long restaurantId;
    private Long addressId;
    private List<Cart> items;

}
