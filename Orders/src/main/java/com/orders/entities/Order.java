package com.orders.entities;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Order {

    private Long orderId;

    private Long userId;

    private Long restaurantId;

    private Enum orderStatus;

    private LocalDateTime createdAt;

    private Long addressId;

    private BigDecimal totalPrice;
}



