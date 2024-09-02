package com.restaurants.service;

import com.restaurants.constant.ConstantMessage;
import com.restaurants.dtoconversion.DtoConversion;
import com.restaurants.entities.RestaurantMenu;
import com.restaurants.exception.NotFoundException;
import com.restaurants.indto.RestaurantMenuRequest;
import com.restaurants.outdto.RestaurantMenuResponse;
import com.restaurants.repository.RestaurantMenuRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing restaurant menu items.
 */
@Service
public class RestaurantMenuService {

    private static final Logger logger = LogManager.getLogger(RestaurantMenuService.class);

    @Autowired
    private RestaurantMenuRepository restaurantMenuRepository;

    @Autowired
    private DtoConversion dtoConversion;

    /**
     * Adds a new food item to the restaurant menu.
     *
     * @param restaurantMenuRequest the request object containing details of the food item
     * @param multipartFile the image file for the food item
     * @return the response object containing details of the added food item
     */
    public RestaurantMenuResponse addFoodItem(final RestaurantMenuRequest restaurantMenuRequest,
                                              final MultipartFile foodImage) {
        logger.info("Adding a new food item with details: {}", restaurantMenuRequest);

        RestaurantMenu restaurantMenu = dtoConversion.convertToRestaurantMenuEntity(restaurantMenuRequest);
        try {
            if (foodImage != null && !foodImage.isEmpty()) {
                logger.info("Processing image file for food item");
                restaurantMenu.setImageUrl(foodImage.getBytes());
            }
        } catch (IOException e) {
            logger.error("Error occurred while processing image file for food item: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        RestaurantMenu savedRestaurantMenu = restaurantMenuRepository.save(restaurantMenu);
        logger.info("Food item added successfully with ID: {}", savedRestaurantMenu.getItemId());

        return dtoConversion.convertToRestaurantMenuResponse(savedRestaurantMenu);
    }

    /**
     * Deletes a food item by its ID.
     *
     * @param itemId the ID of the food item to delete
     * @throws NotFoundException if the food item with the given ID is not found
     */
    public void deleteFoodItem(final Long itemId) throws NotFoundException {
        logger.info("Attempting to delete food item with ID: {}", itemId);

        if (!restaurantMenuRepository.existsById(itemId)) {
            logger.error("Food item with ID: {} not found", itemId);
            throw new NotFoundException(ConstantMessage.FOOD_ITEM_NOT_FOUND);
        }
        restaurantMenuRepository.deleteById(itemId);
        logger.info("Food item with ID: {} deleted successfully", itemId);
    }

    /**
     * Retrieves all food items for a given restaurant ID.
     *
     * @param restaurantId the ID of the restaurant to get food items for
     * @return a list of response objects containing details of food items
     * @throws NotFoundException if no food items are found for the given restaurant ID
     */
    public List<RestaurantMenuResponse> getFoodItemsByRestaurantId(final Long restaurantId) throws NotFoundException {
        logger.info("Retrieving all food items for restaurant ID: {}", restaurantId);

        List<RestaurantMenu> menuList = restaurantMenuRepository.findByRestaurantId(restaurantId);

        if (menuList.isEmpty()) {
            logger.error("No food items found for restaurant ID: {}", restaurantId);
            throw new NotFoundException(ConstantMessage.FOOD_ITEM_NOT_FOUND);
        }

        List<RestaurantMenuResponse> responseList = new ArrayList<>();
        for (RestaurantMenu menu : menuList) {
            RestaurantMenuResponse response = dtoConversion.convertToRestaurantMenuResponse(menu);
            responseList.add(response);
        }
        logger.info("Retrieved {} food items for restaurant ID: {}", responseList.size(), restaurantId);

        return responseList;
    }

    /**
     * Updates an existing food item in the restaurant menu.
     *
     * @param restaurantId the ID of the restaurant to update the menu item for
     * @param menuRequest the request object containing updated details of the food item
     * @return the response object containing updated details of the food item
     * @throws NotFoundException if the food item with the given ID is not found
     */
    public RestaurantMenuResponse updateRestaurantMenu(final Long restaurantId,
                                                       final RestaurantMenuRequest menuRequest) throws NotFoundException {
        logger.info("Updating food item for restaurant ID: {} with details: {}", restaurantId, menuRequest);

        RestaurantMenu existingRestaurantMenu = restaurantMenuRepository.findById(restaurantId)
                .orElseThrow(() -> {
                    logger.error("Food item with ID: {} not found", restaurantId);
                    return new NotFoundException(ConstantMessage.FOOD_ITEM_NOT_FOUND);
                });

        existingRestaurantMenu.setItemName(menuRequest.getItemName());
        existingRestaurantMenu.setPrice(menuRequest.getPrice());
        existingRestaurantMenu.setDescription(menuRequest.getDescription());
        // Update other fields as needed

        RestaurantMenu updatedRestaurantMenu = restaurantMenuRepository.save(existingRestaurantMenu);
        logger.info("Food item with ID: {} updated successfully", restaurantId);

        return dtoConversion.convertToRestaurantMenuResponse(updatedRestaurantMenu);
    }
}
