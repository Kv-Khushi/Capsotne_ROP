//package com.orders.entities;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.orders.enums.OrderStatus;
//import lombok.Data;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Entity
//@Table(name = "orders")
//@Data
//public class Order {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long orderId;
//
//    private Long userId; // fetched via Feign from User microservice
//
//    private Long restaurantId; // fetched via Feign from Restaurant microservice
//
//    private Long addressId;
//
//    private Double totalPrice;
//
//    @Enumerated(EnumType.STRING)
//    private OrderStatus orderStatus;
//
//    private LocalDateTime orderTime;
//
//    private String items;
//
//    @Transient
//    private static final ObjectMapper objectMapper = new ObjectMapper();
//
//    public List<Cart> getCartItemsAsList() throws JsonProcessingException {
//        return objectMapper.readValue(items, objectMapper.getTypeFactory().constructCollectionType(List.class, Cart.class));
//    }
//
//    public void setCartItemsFromList(List<Cart> cartItems) throws JsonProcessingException {
//        this.items = objectMapper.writeValueAsString(cartItems);
//    }
//
//
//
//
//}

package com.orders.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.enums.OrderStatus;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents an order entity in the system.
 * <p>
 * This class maps to the "orders" table in the database and contains information about an order,
 * including the user, restaurant, and address associated with it, the total price, status, time, and the items in the order.
 * </p>
 * <p>
 * The class uses Lombok's {@link Data} annotation to automatically generate getters, setters, and other common methods.
 * </p>
 */
@Entity
@Table(name = "orders")
@Data
public class Order {

    /**
     * The unique identifier for the order.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    /**
     * The unique identifier for the user who placed the order.
     * <p>
     * This value is fetched via Feign from the User microservice.
     * </p>
     */
    private Long userId; // fetched via Feign from User microservice

    /**
     * The unique identifier for the restaurant where the order was placed.
     * <p>
     * This value is fetched via Feign from the Restaurant microservice.
     * </p>
     */
    private Long restaurantId; // fetched via Feign from Restaurant microservice

    /**
     * The unique identifier for the address where the order should be delivered.
     */
    private Long addressId;

    /**
     * The total price of the order.
     */
    private Double totalPrice;

    /**
     * The current status of the order.
     * <p>
     * The status is represented as a string and is defined by the {@link OrderStatus} enum.
     * </p>
     */
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    /**
     * The time when the order was placed.
     */
    private LocalDateTime orderTime;

    /**
     * A JSON string representing the items in the order.
     * <p>
     * The JSON structure should be compatible with the expected format for items.
     * </p>
     */
    private String items;

    @Transient
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Converts the JSON string representation of cart items to a list of {@link Cart} objects.
     *
     * @return a list of {@link Cart} objects
     * @throws JsonProcessingException if there is an error processing the JSON string
     */
    public List<Cart> getCartItemsAsList() throws JsonProcessingException {
        return objectMapper.readValue(items, objectMapper.getTypeFactory().constructCollectionType(List.class, Cart.class));
    }

    /**
     * Converts a list of {@link Cart} objects to a JSON string and sets it as the cart items representation.
     *
     * @param cartItems the list of {@link Cart} objects to convert
     * @throws JsonProcessingException if there is an error processing the list to JSON
     */
    public void setCartItemsFromList(@RequestBody final List<Cart> cartItems) throws JsonProcessingException {
        this.items = objectMapper.writeValueAsString(cartItems);
    }
}

