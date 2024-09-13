package com.restaurants.controller;

import com.restaurants.constant.ConstantMessage;
import com.restaurants.dto.SuccessResponse;
import com.restaurants.exception.NotFoundException;
import com.restaurants.dto.RestaurantRequest;
import com.restaurants.dto.RestaurantResponse;
import com.restaurants.service.RestaurantService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public final class RestaurantController {

 private static final Logger logger = LogManager.getLogger(RestaurantController.class);

 @Autowired
 private RestaurantService restaurantService;

 /**
  * Adds a new restaurant.
  *
  * @param restaurantRequest the request object containing the restaurant details

  * @return a response entity with the created restaurant
  */

 @PostMapping("/addRestaurant")
 public ResponseEntity<SuccessResponse> addRestaurant(@ModelAttribute @Valid RestaurantRequest restaurantRequest,
                                                      @RequestParam("image") MultipartFile image) {
  logger.info("Request to add new restaurant with details: {}", restaurantRequest);

  restaurantService.addRestaurant(restaurantRequest, image);

  logger.info("Restaurant added successfully");

  return ResponseEntity.ok(new SuccessResponse(ConstantMessage.RESTAURANT_ADD_SUCCESS));
 }

 /**
  * Retrieves all restaurants.
  *
  * @return a response entity with a list of all restaurants
  */
 @GetMapping("/allRestaurants")
 public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {
  logger.info("Request to retrieve all restaurants");

  List<RestaurantResponse> restaurantResponses = restaurantService.getAllRestaurants();
  logger.info("Retrieved {} restaurants", restaurantResponses.size());

  return ResponseEntity.ok(restaurantResponses);
 }

 /**
  * Retrieves a restaurant by its ID.
  *
  * @param restaurantId the ID of the restaurant to retrieve
  * @return the restaurant details
  * @throws NotFoundException if the restaurant is not found
  */
 @GetMapping("/getRestaurantById/{restaurantId}")
 public RestaurantResponse getRestaurantById(@PathVariable final Long restaurantId) throws NotFoundException {
  logger.info("Request to retrieve restaurant with ID: {}", restaurantId);

  RestaurantResponse restaurantResponse = restaurantService.getRestaurantById(restaurantId);
  logger.info("Retrieved restaurant with ID: {}", restaurantId);

  return restaurantResponse;
 }
 /**
  * Retrieves the image of a restaurant by its ID.
  *
  * @param restaurantId the ID of the restaurant
  * @return the image data of the restaurant
  * @throws NotFoundException if the restaurant is not found
  */

 @GetMapping("/{restaurantId}/image")
 public ResponseEntity<byte[]> getRestaurantImage(@PathVariable final Long restaurantId) throws NotFoundException {
  logger.info("Retrieving image for restaurant with ID: {}", restaurantId);
  byte[] imageData = restaurantService.getRestaurantImage(restaurantId);
  return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
 }

 /**
  * Retrieves all restaurants associated with a specific user ID.
  *
  * @param userId the ID of the user
  * @return a list of restaurants associated with the user
  */
 @GetMapping("/user/{userId}")
 public ResponseEntity<List<RestaurantResponse>> getAllRestaurantByUserId(@PathVariable final Long userId) {
  logger.info("Retrieving restaurants for user ID: {}", userId);
  List<RestaurantResponse> response = restaurantService.getALlRestaurantsByUserId(userId);
  logger.info("Retrieved {} restaurants for user ID: {}", response.size(), userId);
  return new ResponseEntity<>(response, HttpStatus.OK);
 }
}
