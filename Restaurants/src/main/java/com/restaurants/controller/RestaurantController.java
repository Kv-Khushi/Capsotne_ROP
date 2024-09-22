package com.restaurants.controller;

import com.restaurants.constant.ConstantMessage;
import com.restaurants.dto.SuccessResponse;
import com.restaurants.exception.ResourceNotFoundException;
import com.restaurants.dto.RestaurantRequest;
import com.restaurants.dto.RestaurantResponse;
import com.restaurants.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for managing restaurants.
 * Provides endpoints to add, retrieve, and list restaurants.
 */
@RestController
@RequestMapping("/restaurants")
@Slf4j
public final class RestaurantController {
 

 @Autowired
 private RestaurantService restaurantService;

 /**
  * Adds a new restaurant.
  *
  * @param restaurantRequest the request object containing the restaurant details

  * @return a response entity with the created restaurant
  */

 @PostMapping("/addRestaurant")
 public ResponseEntity<SuccessResponse> addRestaurant(@ModelAttribute @Valid final RestaurantRequest restaurantRequest,
                                                      @RequestParam("image") final MultipartFile image) {
   log.info("Request to add new restaurant with details: {}", restaurantRequest);
   RestaurantResponse restaurantResponse =restaurantService.addRestaurant(restaurantRequest, image);

   log.info("Restaurant added successfully");
   SuccessResponse response= new SuccessResponse(ConstantMessage.RESTAURANT_ADD_SUCCESS);
   return new ResponseEntity<>(response,HttpStatus.CREATED);
 }

 /**
  * Retrieves all restaurants.
  *
  * @return a response entity with a list of all restaurants
  */
 @GetMapping("/allRestaurants")
 public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {
  log.info("Request to retrieve all restaurants");

  List<RestaurantResponse> restaurantResponses = restaurantService.getAllRestaurants();
  log.info("Retrieved {} restaurants", restaurantResponses.size());
  return ResponseEntity.ok(restaurantResponses);
 }

 /**
  * Retrieves a restaurant by its ID.
  *
  * @param restaurantId the ID of the restaurant to retrieve
  * @return the restaurant details
  * @throws ResourceNotFoundException if the restaurant is not found
  */
 @GetMapping("/getRestaurantById/{restaurantId}")
 public RestaurantResponse getRestaurantById(@PathVariable final Long restaurantId) throws ResourceNotFoundException {
  log.info("Request to retrieve restaurant with ID: {}", restaurantId);

  RestaurantResponse restaurantResponse = restaurantService.getRestaurantById(restaurantId);
  log.info("Retrieved restaurant with ID: {}", restaurantId);

  return restaurantResponse;
 }
 /**
  * Retrieves the image of a restaurant by its ID.
  *
  * @param restaurantId the ID of the restaurant
  * @return the image data of the restaurant
  * @throws ResourceNotFoundException if the restaurant is not found
  */

 @GetMapping("/{restaurantId}/image")
 public ResponseEntity<byte[]> getRestaurantImage(@PathVariable final Long restaurantId) {
  log.info("Retrieving image for restaurant with ID: {}", restaurantId);
  byte[] imageData = restaurantService.getRestaurantImage(restaurantId);
  return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
 }

 /**
  * Retrieves all restaurants associated with a specific user ID.
  *
  * @param userId the ID of the user
  * @return a list of restaurants associated with the user
  */
 @GetMapping("/restaurantsByUser/{userId}")
 public ResponseEntity<List<RestaurantResponse>> getAllRestaurantByUserId(@PathVariable final Long userId) {
  log.info("Retrieving restaurants for user ID: {}", userId);
  List<RestaurantResponse> response = restaurantService.getALlRestaurantsByUserId(userId);
  log.info("Retrieved {} restaurants for user ID: {}", response.size(), userId);
  return new ResponseEntity<>(response, HttpStatus.OK);
 }
}
