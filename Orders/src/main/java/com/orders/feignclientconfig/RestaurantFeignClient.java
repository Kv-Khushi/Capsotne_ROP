package com.orders.feignclientconfig;


import com.orders.dto.RestaurantMenuResponse;
import com.orders.dto.RestaurantResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client for interacting with the restaurant-service API.
 */
@FeignClient(name = "restaurant-service", url = "http://localhost:8081")
public interface RestaurantFeignClient {


    /**
     * Fetches the restaurant details by the given restaurant ID.
     *
     * @param restaurantId the ID of the restaurant
     * @return the RestaurantResponse containing restaurant details
     */
    @GetMapping("/restaurants/getRestaurantById/{restaurantId}")
    RestaurantResponse getRestaurantById(@PathVariable("restaurantId") Long restaurantId);

    /**
     * Fetches the menu item details by the given food item ID.
     *
     * @param foodItemId the ID of the food item
     * @return the RestaurantMenuResponse containing food item details
     */
    @GetMapping("/foodItems/{foodItemId}")
    RestaurantMenuResponse getMenuItemById(@PathVariable("foodItemId") Long foodItemId);

}
