package com.restaurants.controller;

import com.restaurants.indto.FoodCategoryRequest;
import com.restaurants.indto.RestaurantRequest;
import com.restaurants.outdto.FoodCategoryResponse;
import com.restaurants.outdto.RestaurantResponse;
import com.restaurants.service.FoodCategoryService;
import com.restaurants.exception.NotFoundException;
import com.restaurants.constant.ConstantMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/foodCategories")
public class FoodCategoryController {

    @Autowired
    private FoodCategoryService foodCategoryService;


    @PostMapping("/add")
    public ResponseEntity<FoodCategoryResponse> addRestaurant(@Valid @RequestBody FoodCategoryRequest foodCategoryRequest) {
        FoodCategoryResponse foodCategoryResponse = foodCategoryService.addFoodCategory(foodCategoryRequest);
        return ResponseEntity.ok(foodCategoryResponse);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<String> deleteFoodCategory(@PathVariable Long categoryId) {
        try {
            foodCategoryService.deleteFoodCategory(categoryId);
            return ResponseEntity.ok("Food category deleted successfully.");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ConstantMessage.CATEGORY_NOT_FOUND);
        }
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<FoodCategoryResponse>> getAllCategoriesByRestaurantId(@PathVariable Long restaurantId) {
        List<FoodCategoryResponse> categories = foodCategoryService.getAllCategoriesByRestaurantId(restaurantId);
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/{categoryId}/name")
    public ResponseEntity<FoodCategoryResponse> updateCategoryName(
            @PathVariable Long categoryId,
            @RequestBody String newCategoryName) throws NotFoundException {
        FoodCategoryResponse updatedCategory = foodCategoryService.updateCategoryName(categoryId, newCategoryName);
        return ResponseEntity.ok(updatedCategory);
    }


    }


