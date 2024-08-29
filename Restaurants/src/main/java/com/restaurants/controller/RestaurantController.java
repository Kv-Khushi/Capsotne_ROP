package com.restaurants.controller;
import com.restaurants.exception.NotFoundException;
import com.restaurants.indto.RestaurantRequest;
import com.restaurants.outdto.RestaurantResponse;
import com.restaurants.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

 @Autowired
 private RestaurantService restaurantService;


 @PostMapping("/addRestaurant")
 public ResponseEntity<RestaurantResponse> addRestaurant(@Valid @RequestBody RestaurantRequest restaurantRequest) {
  RestaurantResponse restaurantResponse = restaurantService.addRestaurant(restaurantRequest);
  return ResponseEntity.ok(restaurantResponse);
 }

 @GetMapping("/allRestaurants")
 public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {
  List<RestaurantResponse> restaurantResponses = restaurantService.getAllRestaurants();
  return ResponseEntity.ok(restaurantResponses);
 }

 @GetMapping("/getRestaurantById/{restaurantId}")
 public RestaurantResponse getRestaurantById(@PathVariable("restaurantId") Long restaurantId) throws NotFoundException {
  return restaurantService.getRestaurantById(restaurantId);
 }

}
