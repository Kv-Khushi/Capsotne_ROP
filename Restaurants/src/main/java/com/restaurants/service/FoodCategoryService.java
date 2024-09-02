package com.restaurants.service;

import com.restaurants.constant.ConstantMessage;
import com.restaurants.dtoconversion.DtoConversion;
import com.restaurants.entities.FoodCategory;
import com.restaurants.exception.NotFoundException;
import com.restaurants.indto.FoodCategoryRequest;
import com.restaurants.outdto.FoodCategoryResponse;
import com.restaurants.repository.FoodCategoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling operations related to food categories.
 */
@Service
public class FoodCategoryService {


    private static final Logger logger = LogManager.getLogger(FoodCategoryService.class);
    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    @Autowired
    private DtoConversion dtoConversion;

    /**
     * Adds a new food category.
     *
     * @param foodCategoryRequest the request object containing category details
     * @return the response object containing details of the added food category
     */
    public FoodCategoryResponse addFoodCategory(final FoodCategoryRequest foodCategoryRequest) {
        logger.info("Adding a new food category with details: {}", foodCategoryRequest);

        FoodCategory foodCategory = dtoConversion.convertToFoodCategoryEntity(foodCategoryRequest);
        FoodCategory savedFoodCategory = foodCategoryRepository.save(foodCategory);

        return dtoConversion.convertToFoodCategoryResponse(savedFoodCategory);
    }

    /**
     * Deletes a food category by its ID.
     *
     * @param categoryId the ID of the category to delete
     * @throws NotFoundException if the category with the given ID is not found
     */
    public void deleteFoodCategory(final Long categoryId) throws NotFoundException {
        logger.info("Attempting to delete food category with ID: {}", categoryId);
        if (!foodCategoryRepository.existsById(categoryId)) {
            logger.error("Food category with ID: {} not found", categoryId);
            throw new NotFoundException(ConstantMessage.CATEGORY_NOT_FOUND);
        }
        foodCategoryRepository.deleteById(categoryId);
        logger.info("Food category with ID: {} deleted successfully", categoryId);
    }

    /**
     * Retrieves all food categories for a given restaurant ID.
     *
     * @param restaurantId the ID of the restaurant to get categories for
     * @return a list of response objects containing food category details
     */
    public List<FoodCategoryResponse> getAllCategoriesByRestaurantId(final Long restaurantId) {
        logger.info("Retrieving all food categories for restaurant ID: {}", restaurantId);
        List<FoodCategory> categories = foodCategoryRepository.findByRestaurantId(restaurantId);
        List<FoodCategoryResponse> responseList = new ArrayList<>();

        for (FoodCategory category : categories) {
            FoodCategoryResponse response = dtoConversion.convertToFoodCategoryResponse(category);
            responseList.add(response);
        }
        logger.info("Retrieved {} food categories for restaurant ID: {}", responseList.size(), restaurantId);
        return responseList;
    }

    /**
     * Updates the name of an existing food category.
     *
     * @param categoryId the ID of the category to update
     * @param newCategoryName the new name to set for the category
     * @return the response object containing updated category details
     * @throws NotFoundException if the category with the given ID is not found
     */

    public FoodCategoryResponse updateCategoryName(final Long categoryId, final String newCategoryName) throws NotFoundException {
        logger.info("Updating category name for category ID: {} to new name: {}", categoryId, newCategoryName);

        FoodCategory existingCategory = foodCategoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    logger.error("Food category with ID: {} not found", categoryId);
                    return new NotFoundException(ConstantMessage.CATEGORY_NOT_FOUND);
                });

        existingCategory.setCategoryName(newCategoryName);

        FoodCategory updatedCategory = foodCategoryRepository.save(existingCategory);
        logger.info("Category name updated successfully for ID: {}", categoryId);

        return dtoConversion.convertToFoodCategoryResponse(updatedCategory);
    }

}
