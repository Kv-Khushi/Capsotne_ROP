package com.restaurants.controller;


import com.restaurants.constant.ConstantMessage;
import com.restaurants.dtoconversion.DtoConversion;
import com.restaurants.exception.NotFoundException;
import com.restaurants.indto.FoodCategoryRequest;
import com.restaurants.indto.RestaurantMenuRequest;
import com.restaurants.outdto.FoodCategoryResponse;
import com.restaurants.outdto.RestaurantMenuResponse;
import com.restaurants.outdto.RestaurantResponse;
import com.restaurants.repository.RestaurantMenuRepository;
import com.restaurants.service.RestaurantMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/foodItems")
@Service
public class RestaurantMenuController {

    @Autowired
    private RestaurantMenuService restaurantMenuService;

    @PostMapping("/add")
    public ResponseEntity<RestaurantMenuResponse> addFoodItem( @RequestBody RestaurantMenuRequest restaurantMenuRequest) {
    RestaurantMenuResponse restaurantMenuResponse  = restaurantMenuService.addFoodItem(restaurantMenuRequest);
        return ResponseEntity.ok(restaurantMenuResponse);
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<Void> deleteFoodItem(@PathVariable Long itemId) throws NotFoundException {
        restaurantMenuService.deleteFoodItem(itemId);
        return ResponseEntity.noContent().build();
    }


}

