package com.orders.dto;
import com.orders.enums.OrderStatus;
import lombok.Data;
import java.time.LocalDateTime;


@Data
public class OrderResponse {

    private Long orderId;
    private Double totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime orderTime;
    private String items; // List of food items



}
