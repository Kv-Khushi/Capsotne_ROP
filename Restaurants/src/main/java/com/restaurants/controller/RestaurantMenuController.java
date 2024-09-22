package com.restaurants.controller;

import com.restaurants.constant.ConstantMessage;
import com.restaurants.dto.SuccessResponse;
import com.restaurants.exception.ResourceNotFoundException;
import com.restaurants.dto.RestaurantMenuRequest;
import com.restaurants.dto.RestaurantMenuResponse;
import com.restaurants.service.RestaurantMenuService;
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
 * Controller for managing restaurant menu items.
 * Provides endpoints to add, update, and retrieve food items.
 */
@RestController
@RequestMapping("/foodItems")
@Slf4j
public final class RestaurantMenuController {


    @Autowired
    private RestaurantMenuService restaurantMenuService;


    /**
     * Adds a new food item to the restaurant menu.
     *
     * @param restaurantMenuRequest the request object containing food item details

     * @return a response entity with the created food item
     */
    @PostMapping("/add")
    public ResponseEntity<SuccessResponse> addFoodItem(@Valid @ModelAttribute final RestaurantMenuRequest restaurantMenuRequest,
                                                       @RequestParam("image") final MultipartFile image) {
        log.info("Request to add a new food item: {}", restaurantMenuRequest);
        restaurantMenuService.addFoodItem(restaurantMenuRequest, image);
        log.info("Food item added successfully");
       SuccessResponse response =new SuccessResponse(ConstantMessage.FOOD_ITEM_ADD_SUCCESS);
        //return ResponseEntity.ok(new SuccessResponse(ConstantMessage.FOOD_ITEM_ADD_SUCCESS));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieves all food items for a given restaurant.
     *
     * @param restaurantId the ID of the restaurant
     * @return a response entity with the list of food items
     * @throws ResourceNotFoundException if the restaurant is not found
     */
    @GetMapping("/getFoodItems/{restaurantId}")
    public ResponseEntity<List<RestaurantMenuResponse>> getFoodItemsByRestaurantId(
            @PathVariable final Long restaurantId) throws ResourceNotFoundException {
        log.info("Request to retrieve food items for restaurant ID: {}", restaurantId);

        List<RestaurantMenuResponse> restaurantMenuResponses = restaurantMenuService.getFoodItemsByRestaurantId(restaurantId);
        log.info("Retrieved {} food items for restaurant ID: {}", restaurantMenuResponses.size(), restaurantId);

        return ResponseEntity.ok(restaurantMenuResponses);
    }

    /**
     * Updates a food item in the restaurant menu.
     *
     * @param itemId the ID of the foodItem
     * @param menuRequest  the request object containing updated food item details
     * @return a response entity with the updated food item
     * @throws ResourceNotFoundException if the restaurant or food item is not found
     */
    @PutMapping("/update/{itemId}")
    public ResponseEntity<RestaurantMenuResponse> updateRestaurantMenu(
            @PathVariable final Long itemId,
            @RequestBody final RestaurantMenuRequest menuRequest) {

        log.info("Request to update food item for restaurant ID: {}", itemId);
        RestaurantMenuResponse updatedMenu = restaurantMenuService.updateRestaurantMenu(itemId, menuRequest);
        log.info("Food item for restaurant ID: {} updated successfully", itemId);
        return ResponseEntity.ok(updatedMenu);
    }


    /**
     * Retrieves a food item by its ID.
     *
     * @param foodItemId the ID of the food item
     * @return a response entity with the food item details
     */
    @GetMapping("/{foodItemId}")
    public ResponseEntity<RestaurantMenuResponse> getFoodItemById(@PathVariable("foodItemId") final Long foodItemId) {
        RestaurantMenuResponse menuResponse = restaurantMenuService.getFoodItemById(foodItemId);
        return ResponseEntity.ok(menuResponse);
    }

    /**
     * Retrieves the image of a food item by its ID.
     *
     * @param foodItemId the ID of the food item
     * @return a response entity with the image of the food item
     */
    @GetMapping("/{foodItemId}/image")
    public ResponseEntity<byte[]> getFoodItemImage(@PathVariable final Long foodItemId){
        log.info("Retrieving image for food item with ID: {}", foodItemId);
        byte[] imageData = restaurantMenuService.getFoodItemImage(foodItemId);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
    }


    /**
     * Retrieves all food items for a specific category.
     *
     * @param categoryId the ID of the category
     * @return a response entity with the list of food items
     * @throws ResourceNotFoundException if the category is not found
     */
    @GetMapping("/getFoodItemsByCtg/{categoryId}")
    public ResponseEntity<List<RestaurantMenuResponse>> getFoodItemsByCategoryId(
            @PathVariable final Long categoryId) {
        log.info("Request to retrieve food items for restaurant ID: {}", categoryId);

        List<RestaurantMenuResponse> restaurantMenuResponses= restaurantMenuService.getFoodItemsByCategoryId(categoryId);
        log.info("Retrieved {} food items for restaurant ID: {}", restaurantMenuResponses.size(), categoryId);
        return ResponseEntity.ok(restaurantMenuResponses);
    }

}
