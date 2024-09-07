package com.orders.feignclientconfig;


import com.orders.outdto.RestaurantMenuResponse;
import com.orders.outdto.RestaurantResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "restaurant-service", url = "http://localhost:8081")
public interface RestaurantFeignClient {

    @GetMapping("/restaurants/getRestaurantById/{restaurantId}")
    RestaurantResponse getRestaurantById(@PathVariable("restaurantId") Long restaurantId);

    @GetMapping("/foodItems/{foodItemId}")
    RestaurantMenuResponse getMenuItemById(@PathVariable("foodItemId") Long foodItemId);

}
