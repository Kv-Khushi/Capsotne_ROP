package com.orders.outdto;
import com.orders.enums.OrderStatus;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {

    private Long orderId;
    private UserResponse user; // Fetched from Users microservice via Feign
    private RestaurantResponse restaurant; // Fetched from Restaurants microservice via Feign
    private Double totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime orderTime;
    private List<CartResponse> items; // List of food items
    private Long AddressId;


}
