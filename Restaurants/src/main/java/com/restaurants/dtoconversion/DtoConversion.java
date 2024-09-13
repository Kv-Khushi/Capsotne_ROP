package com.restaurants.dtoconversion;

import com.restaurants.entities.FoodCategory;
import com.restaurants.entities.Restaurant;
import com.restaurants.entities.RestaurantMenu;
import com.restaurants.dto.FoodCategoryRequest;
import com.restaurants.dto.RestaurantMenuRequest;
import com.restaurants.dto.RestaurantRequest;
import com.restaurants.dto.FoodCategoryResponse;
import com.restaurants.dto.RestaurantMenuResponse;
import com.restaurants.dto.RestaurantResponse;
import org.springframework.stereotype.Component;

/**
 * Provides methods for converting between DTOs and entities.
 */
@Component
public final class DtoConversion {

    /**
     * Converts a {@link RestaurantRequest} to a {@link Restaurant} entity.
     *
     * @param restaurantRequest the request object containing restaurant details
     * @return a {@link Restaurant} entity
     */
    public Restaurant convertToRestaurantEntity(final RestaurantRequest restaurantRequest) {
        final Restaurant restaurant = new Restaurant();
        restaurant.setUserId(restaurantRequest.getUserId());
        restaurant.setRestaurantName(restaurantRequest.getRestaurantName());
        restaurant.setRestaurantAddress(restaurantRequest.getRestaurantAddress());
        restaurant.setContactNumber(restaurantRequest.getContactNumber());
        restaurant.setRestaurantDescription(restaurantRequest.getRestaurantDescription());
        restaurant.setOpeningHour(restaurantRequest.getOpeningHour());
        return restaurant;
    }

    /**
     * Converts a {@link Restaurant} entity to a {@link RestaurantResponse}.
     *
     * @param restaurant the {@link Restaurant} entity
     * @return a {@link RestaurantResponse} containing restaurant details
     */
    public static RestaurantResponse convertToRestaurantResponse(final Restaurant restaurant) {
        final RestaurantResponse restaurantResponse = new RestaurantResponse();
        restaurantResponse.setRestaurantId(restaurant.getRestaurantId());
        restaurantResponse.setUserId(restaurant.getUserId());
        restaurantResponse.setRestaurantName(restaurant.getRestaurantName());
        restaurantResponse.setRestaurantAddress(restaurant.getRestaurantAddress());
        restaurantResponse.setContactNumber(restaurant.getContactNumber());
        restaurantResponse.setRestaurantDescription(restaurant.getRestaurantDescription());
        restaurantResponse.setOpeningHour(restaurant.getOpeningHour());
        restaurantResponse.setRestaurantImage(restaurant.getRestaurantImage());
        return restaurantResponse;
    }

    /**
     * Converts a {@link FoodCategoryRequest} to a {@link FoodCategory} entity.
     *
     * @param foodCategoryRequest the request object containing food category details
     * @return a {@link FoodCategory} entity
     */
    public FoodCategory convertToFoodCategoryEntity(final FoodCategoryRequest foodCategoryRequest) {
        final FoodCategory foodCategory = new FoodCategory();
        foodCategory.setRestaurantId(foodCategoryRequest.getRestaurantId());
        foodCategory.setCategoryName(foodCategoryRequest.getCategoryName());
        return foodCategory;
    }

    /**
     * Converts a {@link FoodCategory} entity to a {@link FoodCategoryResponse}.
     *
     * @param foodCategory the {@link FoodCategory} entity
     * @return a {@link FoodCategoryResponse} containing food category details
     */
    public FoodCategoryResponse convertToFoodCategoryResponse(final FoodCategory foodCategory) {
        final FoodCategoryResponse response = new FoodCategoryResponse();
        response.setCategoryId(foodCategory.getCategoryId());
        response.setRestaurantId(foodCategory.getRestaurantId());
        response.setCategoryName(foodCategory.getCategoryName());
        return response;
    }

    /**
     * Converts a {@link RestaurantMenuRequest} to a {@link RestaurantMenu} entity.
     *
     * @param request the request object containing menu details
     * @return a {@link RestaurantMenu} entity
     */
    public RestaurantMenu convertToRestaurantMenuEntity(final RestaurantMenuRequest request) {
        final RestaurantMenu menu = new RestaurantMenu();
        menu.setItemName(request.getItemName());
        menu.setPrice(request.getPrice());
        menu.setDescription(request.getDescription());
        menu.setVegNonVeg(request.getVegNonVeg());
        menu.setCategoryId(request.getCategoryId());
        menu.setRestaurantId(request.getRestaurantId());
        return menu;
    }

    /**
     * Converts a {@link RestaurantMenu} entity to a {@link RestaurantMenuResponse}.
     *
     * @param menu the {@link RestaurantMenu} entity
     * @return a {@link RestaurantMenuResponse} containing menu details
     */
    public RestaurantMenuResponse convertToRestaurantMenuResponse(final RestaurantMenu menu) {
        final RestaurantMenuResponse response = new RestaurantMenuResponse();
        response.setItemId(menu.getItemId());
        response.setItemName(menu.getItemName());
        response.setPrice(menu.getPrice());
        response.setDescription(menu.getDescription());
        response.setVegNonVeg(menu.getVegNonVeg());
        response.setCategoryId(menu.getCategoryId());
        response.setRestaurantId(menu.getRestaurantId());
        response.setImageUrl(menu.getImageUrl());
        return response;
    }
}
