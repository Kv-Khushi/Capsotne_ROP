package com.restaurants.service;

import com.restaurants.constant.ConstantMessage;
import com.restaurants.dtoconversion.DtoConversion;
import com.restaurants.entities.RestaurantMenu;
import com.restaurants.exception.AlreadyExistsException;
import com.restaurants.exception.ResourceNotFoundException;
import com.restaurants.dto.RestaurantMenuRequest;
import com.restaurants.dto.RestaurantMenuResponse;
import com.restaurants.repository.FoodCategoryRepository;
import com.restaurants.repository.RestaurantMenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing restaurant menu items.
 */
@Service
@Slf4j
public class RestaurantMenuService {


    @Autowired
    private RestaurantMenuRepository restaurantMenuRepository;

    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    @Autowired
    private DtoConversion dtoConversion;

    /**
     * Adds a new food item to the restaurant menu.
     *
     * @param restaurantMenuRequest the request object containing details of the food item

     * @return the response object containing details of the added food item
     */
    public RestaurantMenuResponse addFoodItem(final RestaurantMenuRequest restaurantMenuRequest,
                                              final MultipartFile foodImage) {
        log.info("Adding a new food item with details: {}", restaurantMenuRequest);

        // Check if the item already exists for the restaurant
        boolean exists = restaurantMenuRepository.existsByRestaurantIdAndItemNameIgnoreCase(
                restaurantMenuRequest.getRestaurantId(),
                restaurantMenuRequest.getItemName()
        );
        boolean categoryExists = foodCategoryRepository.existsById(restaurantMenuRequest.getCategoryId());

        if(!categoryExists){
            throw new ResourceNotFoundException(ConstantMessage.CATEGORY_NOT_FOUND);
        }

        if (exists) {
            log.error("Duplicate item: {} already exists for restaurant ID: {}",
                    restaurantMenuRequest.getItemName(),
                    restaurantMenuRequest.getRestaurantId());
            throw new AlreadyExistsException(ConstantMessage.FOOD_ITEM_ALREADY_EXISTS);
        }

        RestaurantMenu restaurantMenu = dtoConversion.convertToRestaurantMenuEntity(restaurantMenuRequest);
        try {
            if (foodImage != null && !foodImage.isEmpty()) {
                log.info("Processing image file for food item");
                restaurantMenu.setImageUrl(foodImage.getBytes());
            }
        } catch (IOException e) {
            log.error("Error occurred while processing image file for food item: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        RestaurantMenu savedRestaurantMenu = restaurantMenuRepository.save(restaurantMenu);
        log.info("Food item added successfully with ID: {}", savedRestaurantMenu.getItemId());

        return dtoConversion.convertToRestaurantMenuResponse(savedRestaurantMenu);
    }

    /**
     * Deletes a food item by its ID.
     *
     * @param itemId the ID of the food item to delete
     * @throws ResourceNotFoundException if the food item with the given ID is not found
     */
    public void deleteFoodItem(final Long itemId) throws ResourceNotFoundException {
        log.info("Attempting to delete food item with ID: {}", itemId);

        if (!restaurantMenuRepository.existsById(itemId)) {
            log.error("Food item with ID: {} not found", itemId);
            throw new ResourceNotFoundException(ConstantMessage.FOOD_ITEM_NOT_FOUND);
        }
        restaurantMenuRepository.deleteById(itemId);
        log.info("Food item with ID: {} deleted successfully", itemId);
    }

    /**
     * Retrieves all food items for a given restaurant ID.
     *
     * @param restaurantId the ID of the restaurant to get food items for
     * @return a list of response objects containing details of food items
     * @throws ResourceNotFoundException if no food items are found for the given restaurant ID
     */
    public List<RestaurantMenuResponse> getFoodItemsByRestaurantId(final Long restaurantId) throws ResourceNotFoundException {
        log.info("Retrieving all food items for restaurant ID: {}", restaurantId);

        List<RestaurantMenu> menuList = restaurantMenuRepository.findByRestaurantId(restaurantId);

        if (menuList.isEmpty()) {
            log.error("No food items found for restaurant ID: {}", restaurantId);
            throw new ResourceNotFoundException(ConstantMessage.FOOD_ITEM_NOT_FOUND);
        }

        List<RestaurantMenuResponse> responseList = new ArrayList<>();
        for (RestaurantMenu menu : menuList) {
            RestaurantMenuResponse response = dtoConversion.convertToRestaurantMenuResponse(menu);
            responseList.add(response);
        }
        log.info("Retrieved {} food items for restaurant ID: {}", responseList.size(), restaurantId);

        return responseList;
    }

    /**
     * Updates an existing food item in the restaurant menu.
     *
     * @param itemId the ID of the item to update the menu
     * @param menuRequest the request object containing updated details of the food item
     * @return the response object containing updated details of the food item
     * @throws ResourceNotFoundException if the food item with the given ID is not found
     */
    public RestaurantMenuResponse updateRestaurantMenu(final Long itemId,
                                                       final RestaurantMenuRequest menuRequest) {
        log.info("Updating food item for restaurant ID: {} with details: {}",itemId, menuRequest);

        RestaurantMenu existingRestaurantMenu = restaurantMenuRepository.findById(itemId)
                .orElseThrow(() -> {
                    log.error("Food item with ID: {} not found", itemId);
                    return new ResourceNotFoundException(ConstantMessage.FOOD_ITEM_NOT_FOUND);
                });

        existingRestaurantMenu.setItemName(menuRequest.getItemName());
        existingRestaurantMenu.setPrice(menuRequest.getPrice());
        existingRestaurantMenu.setDescription(menuRequest.getDescription());


        RestaurantMenu updatedRestaurantMenu = restaurantMenuRepository.save(existingRestaurantMenu);
        log.info("Food item with ID: {} updated successfully",itemId);

        return dtoConversion.convertToRestaurantMenuResponse(updatedRestaurantMenu);
    }

    /**
     * Retrieves a food item by its ID.
     *
     * @param foodItemId the ID of the food item to retrieve
     * @return the response object containing details of the food item
     * @throws ResourceNotFoundException if the food item with the given ID is not found
     */
    public RestaurantMenuResponse getFoodItemById(final Long foodItemId) throws ResourceNotFoundException {
        RestaurantMenu menuItem = restaurantMenuRepository.findById(foodItemId)
                .orElseThrow(() -> new ResourceNotFoundException(ConstantMessage.FOOD_ITEM_NOT_FOUND));

        RestaurantMenuResponse response = new RestaurantMenuResponse();
        response.setItemId(menuItem.getItemId());
        response.setItemName(menuItem.getItemName());
        response.setPrice(menuItem.getPrice());
        response.setDescription(menuItem.getDescription());
        response.setVegNonVeg(menuItem.getVegNonVeg());
        response.setCategoryId(menuItem.getCategoryId());
        response.setRestaurantId(menuItem.getRestaurantId());
        response.setImageUrl(menuItem.getImageUrl());

        return response;
    }


    /**
     * Retrieves the image of a food item by its ID.
     *
     * @param foodItemId the ID of the food item to retrieve the image for
     * @return the image bytes of the food item
     * @throws ResourceNotFoundException if the food item with the given ID is not found
     */
    public byte[] getFoodItemImage(final Long foodItemId) throws ResourceNotFoundException {
        log.info("Fetching image for food item with ID: {}", foodItemId);
        RestaurantMenuResponse foodItem = getFoodItemById(foodItemId);
        return foodItem.getImageUrl(); // Assuming this returns byte[]
    }


    /**
     * Finds food items by category ID.
     *
     * @param categoryId the ID of the category to find food items for
     * @return a list of response objects containing details of food items
     * @throws ResourceNotFoundException if no food items are found for the given category ID
     */
    @Transactional
    public List<RestaurantMenuResponse> getFoodItemsByCategoryId(final Long categoryId){
        log.info("Retrieving all food items for restaurant ID: {}", categoryId);

        List<RestaurantMenu> menuList = restaurantMenuRepository.findByCategoryId(categoryId);
        if (menuList.isEmpty()) {
            log.error("No food items found for restaurant ID: {}",categoryId);
            throw new ResourceNotFoundException(ConstantMessage.FOOD_ITEM_NOT_FOUND);
        }
        List<RestaurantMenuResponse> responseList = new ArrayList<>();
        for (RestaurantMenu menu : menuList) {
            RestaurantMenuResponse response = dtoConversion.convertToRestaurantMenuResponse(menu);
            responseList.add(response);
        }
        log.info("Retrieved {} food items for restaurant ID: {}", responseList.size(), categoryId);

        return responseList;
    }

}
