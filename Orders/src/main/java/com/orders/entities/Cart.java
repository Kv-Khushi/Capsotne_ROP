package com.orders.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    private Long userId; // Foreign key to Order (manually handled)

    private Long foodItemId; // fetched via Feign from Restaurant microservice

    private Integer quantity;

    private Long restaurantId;

    private Double pricePerItem;

}
