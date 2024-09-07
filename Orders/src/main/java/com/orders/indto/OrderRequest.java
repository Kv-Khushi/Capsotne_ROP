package com.orders.indto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private Long userId;
    private Long restaurantId;
    private Long AddressId;
    private List<CartRequest> items;

}
