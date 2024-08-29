package com.restaurants.service;

import com.restaurants.constant.ConstantMessage;
import com.restaurants.dtoconversion.DtoConversion;
import com.restaurants.entities.FoodCategory;
import com.restaurants.exception.NotFoundException;
import com.restaurants.indto.FoodCategoryRequest;
import com.restaurants.outdto.FoodCategoryResponse;
import com.restaurants.repository.FoodCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodCategoryService {

    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    @Autowired
    private DtoConversion dtoConversion;

    public FoodCategoryResponse addFoodCategory(FoodCategoryRequest foodCategoryRequest) {

        FoodCategory foodCategory = dtoConversion.convertToFoodCategoryEntity(foodCategoryRequest);
        FoodCategory savedFoodCategory = foodCategoryRepository.save(foodCategory);
        return dtoConversion.convertToFoodCategoryResponse(savedFoodCategory);
    }

    public void deleteFoodCategory(Long categoryId) throws NotFoundException {
        if (!foodCategoryRepository.existsById(categoryId)) {
            throw new NotFoundException(ConstantMessage.CATEGORY_NOT_FOUND);
        }
        foodCategoryRepository.deleteById(categoryId);
    }

    public List<FoodCategoryResponse> getAllCategoriesByRestaurantId(Long restaurantId) {
        List<FoodCategory> categories = foodCategoryRepository.findByRestaurantId(restaurantId);
        List<FoodCategoryResponse> responseList = new ArrayList<>();

        for (FoodCategory category : categories) {
            FoodCategoryResponse response = dtoConversion.convertToFoodCategoryResponse(category);
            responseList.add(response);
        }
        return responseList;
    }


    public FoodCategoryResponse updateCategoryName(Long categoryId, String newCategoryName) throws NotFoundException {
        FoodCategory existingCategory = foodCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(ConstantMessage.CATEGORY_NOT_FOUND));

        existingCategory.setCategoryName(newCategoryName);

        FoodCategory updatedCategory = foodCategoryRepository.save(existingCategory);
        return dtoConversion.convertToFoodCategoryResponse(updatedCategory);
    }

}
