package com.restaurants.dtoconversion;
import com.restaurants.entities.FoodCategory;
import com.restaurants.entities.Restaurant;
import com.restaurants.entities.RestaurantMenu;
import com.restaurants.indto.FoodCategoryRequest;
import com.restaurants.indto.RestaurantMenuRequest;
import com.restaurants.indto.RestaurantRequest;
import com.restaurants.outdto.FoodCategoryResponse;
import com.restaurants.outdto.RestaurantMenuResponse;
import com.restaurants.outdto.RestaurantResponse;
import org.springframework.stereotype.Component;

@Component
public class DtoConversion {

    public Restaurant convertToRestaurantEntity(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = new Restaurant();
        restaurant.setUserId(restaurantRequest.getUserId());
        restaurant.setRestaurantName(restaurantRequest.getRestaurantName());
        restaurant.setRestaurantAddress(restaurantRequest.getRestaurantAddress());
        restaurant.setContactNumber(restaurantRequest.getContactNumber());
        restaurant.setRestaurantDescription(restaurantRequest.getRestaurantDescription());
        restaurant.setOpeningHour(restaurantRequest.getOpeningHour());
        restaurant.setRestaurantImages(restaurantRequest.getRestaurantImages());
        return restaurant;
    }

    public RestaurantResponse convertToRestaurantResponse(Restaurant restaurant) {
        RestaurantResponse restaurantResponse = new RestaurantResponse();
        restaurantResponse.setUserId(restaurant.getUserId());
        restaurantResponse.setRestaurantName(restaurant.getRestaurantName());
        restaurantResponse.setRestaurantAddress(restaurant.getRestaurantAddress());
        restaurantResponse.setContactNumber(restaurant.getContactNumber());
        restaurantResponse.setRestaurantDescription(restaurant.getRestaurantDescription());
        restaurantResponse.setOpeningHour(restaurant.getOpeningHour());
        restaurantResponse.setRestaurantImages(restaurant.getRestaurantImages());
        return restaurantResponse;
    }


        public FoodCategory convertToFoodCategoryEntity(FoodCategoryRequest foodCategoryRequest) {
            FoodCategory foodCategory = new FoodCategory();
            foodCategory.setRestaurantId(foodCategoryRequest.getRestaurantId());
            foodCategory.setCategoryName(foodCategoryRequest.getCategoryName());
            return foodCategory;
        }

        public FoodCategoryResponse convertToFoodCategoryResponse(FoodCategory foodCategory) {
            FoodCategoryResponse response = new FoodCategoryResponse();
            response.setCategoryId(foodCategory.getCategoryId());
            response.setRestaurantId(foodCategory.getRestaurantId());
            response.setCategoryName(foodCategory.getCategoryName());
            return response;
        }


    public RestaurantMenu convertToRestaurantMenuEntity(RestaurantMenuRequest request) {
        RestaurantMenu menu = new RestaurantMenu();
        menu.setItemName(request.getItemName());
        menu.setPrice(request.getPrice());
        menu.setDescription(request.getDescription());
        menu.setVegNonVeg(request.getVegNonVeg());
        menu.setCategoryId(request.getCategoryId());
        menu.setRestaurantId(request.getRestaurantId());
        menu.setImageUrl(request.getImageUrl());
        return menu;
    }

    public RestaurantMenuResponse convertToRestaurantMenuResponse(RestaurantMenu menu) {
        RestaurantMenuResponse response = new RestaurantMenuResponse();
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

