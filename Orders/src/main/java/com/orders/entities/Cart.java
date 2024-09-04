package com.orders.entities;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;


    private Long foodItemsId;

    private Long foodQuantity;

    private BigDecimal pricePerQuantity;


    private Long userId;
}
