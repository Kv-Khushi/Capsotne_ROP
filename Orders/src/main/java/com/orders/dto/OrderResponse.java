package com.orders.dto;
import com.orders.enums.OrderStatus;
import lombok.Data;
import java.time.LocalDateTime;


/**
 * Data Transfer Object (DTO) representing the response for an order.
 * This class encapsulates details about the order, such as its ID,
 * total price, status, time, and the list of ordered food items.
 * <p>
 * Lombok's {@code @Data} annotation is used to automatically generate
 * getters, setters, equals, hashCode, and toString methods.
 * </p>
 */
@Data
public class OrderResponse {

    /**
     * The unique ID of the order.
     */
    private Long orderId;

    /**
     * The total price of the order.
     */
    private Double totalPrice;

    /**
     * The current status of the order (e.g., PENDING, COMPLETED, CANCELED).
     * The {@code OrderStatus} enum defines the possible statuses.
     */
    private OrderStatus orderStatus;

    /**
     * The date and time when the order was placed.
     */
    private LocalDateTime orderTime;

    /**
     * A string representing the list of ordered food items.
     * This could be a serialized list of items as a comma-separated string.
     */
    private String items; // List of food items



}
