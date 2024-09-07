package com.orders.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.enums.OrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long userId; // fetched via Feign from User microservice

    private Long restaurantId; // fetched via Feign from Restaurant microservice

    private Long addressId;

    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime orderTime;

    private String items;

    @Transient
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public List<Cart> getCartItemsAsList() throws JsonProcessingException {
        return objectMapper.readValue(items, objectMapper.getTypeFactory().constructCollectionType(List.class, Cart.class));
    }

    public void setCartItemsFromList(List<Cart> cartItems) throws JsonProcessingException {
        this.items = objectMapper.writeValueAsString(cartItems);
    }




}
