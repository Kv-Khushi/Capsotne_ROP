package com.restaurants.controller;

import com.restaurants.constant.ConstantMessage;
import com.restaurants.dto.outdto.SuccessResponse;
import com.restaurants.entities.RestaurantMenu;
import com.restaurants.exception.NotFoundException;
import com.restaurants.dto.indto.RestaurantMenuRequest;
import com.restaurants.dto.outdto.RestaurantMenuResponse;
import com.restaurants.service.RestaurantMenuService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public final class RestaurantMenuController {

    private static final Logger logger = LogManager.getLogger(RestaurantMenuController.class);

    @Autowired
    private RestaurantMenuService restaurantMenuService;



    /**
     * Adds a new food item to the restaurant menu.
     *
     * @param restaurantMenuRequest the request object containing food item details

     * @return a response entity with the created food item
     */
    @PostMapping("/add")
    public ResponseEntity<SuccessResponse> addFoodItem(@Valid @ModelAttribute RestaurantMenuRequest restaurantMenuRequest,
                                                       @RequestParam("image") MultipartFile image) {
        logger.info("Request to add a new food item: {}", restaurantMenuRequest);

        restaurantMenuService.addFoodItem(restaurantMenuRequest, image);

        logger.info("Food item added successfully");

        return ResponseEntity.ok(new SuccessResponse(ConstantMessage.FOOD_ITEM_ADD_SUCCESS));
    }

    /**
     * Retrieves all food items for a given restaurant.
     *
     * @param restaurantId the ID of the restaurant
     * @return a response entity with the list of food items
     * @throws NotFoundException if the restaurant is not found
     */
    @GetMapping("/getFoodItems/{restaurantId}")
    public ResponseEntity<List<RestaurantMenuResponse>> getFoodItemsByRestaurantId(
            @PathVariable final Long restaurantId) throws NotFoundException {
        logger.info("Request to retrieve food items for restaurant ID: {}", restaurantId);

        List<RestaurantMenuResponse> restaurantMenuResponses = restaurantMenuService.getFoodItemsByRestaurantId(restaurantId);
        logger.info("Retrieved {} food items for restaurant ID: {}", restaurantMenuResponses.size(), restaurantId);

        return ResponseEntity.ok(restaurantMenuResponses);
    }

    /**
     * Updates a food item in the restaurant menu.
     *
     * @param restaurantId the ID of the restaurant
     * @param menuRequest  the request object containing updated food item details
     * @return a response entity with the updated food item
     * @throws NotFoundException if the restaurant or food item is not found
     */
    @PutMapping("/update/{restaurantId}")
    public ResponseEntity<RestaurantMenuResponse> updateRestaurantMenu(
            @PathVariable final Long restaurantId,
            @RequestBody final RestaurantMenuRequest menuRequest) {
        logger.info("Request to update food item for restaurant ID: {} with details: {}", restaurantId, menuRequest);

        try {
            RestaurantMenuResponse updatedMenu = restaurantMenuService.updateRestaurantMenu(restaurantId, menuRequest);
            logger.info("Food item updated successfully with ID: {}", updatedMenu.getItemId());
            return new ResponseEntity<>(updatedMenu, HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("Error updating food item for restaurant ID: {}: {}", restaurantId, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{foodItemId}")
    public ResponseEntity<RestaurantMenuResponse> getFoodItemById(@PathVariable("foodItemId") Long foodItemId) {
        try {
            RestaurantMenuResponse menuResponse = restaurantMenuService.getFoodItemById(foodItemId);
            return ResponseEntity.ok(menuResponse);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
