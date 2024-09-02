package com.restaurants.dtoconversion;

import com.restaurants.entities.FoodCategory;
import com.restaurants.entities.RestaurantMenu;
import com.restaurants.indto.FoodCategoryRequest;
import com.restaurants.indto.RestaurantMenuRequest;
import com.restaurants.indto.RestaurantRequest;
import com.restaurants.outdto.FoodCategoryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.restaurants.entities.Restaurant;


import static org.junit.jupiter.api.Assertions.*;

class DtoConversionTest {

    private DtoConversion dtoConversion;

    @BeforeEach
    void setUp() {
        dtoConversion = new DtoConversion();
    }

    @Test
    void testConvertToRestaurantEntity() {
        RestaurantRequest request = new RestaurantRequest();
        request.setUserId(1L);
        request.setRestaurantName("Test Restaurant");
        request.setRestaurantAddress("123 Test St");
        request.setContactNumber(1234567890L);
        request.setRestaurantDescription("Test Description");
        request.setOpeningHour("10");

        Restaurant restaurant = dtoConversion.convertToRestaurantEntity(request);

        assertNotNull(restaurant);
        assertEquals(request.getUserId(), restaurant.getUserId());
        assertEquals(request.getRestaurantName(), restaurant.getRestaurantName());
        assertEquals(request.getRestaurantAddress(), restaurant.getRestaurantAddress());
        assertEquals(request.getContactNumber(), restaurant.getContactNumber());
        assertEquals(request.getRestaurantDescription(), restaurant.getRestaurantDescription());
        assertEquals(request.getOpeningHour(), restaurant.getOpeningHour());
    }


    @Test
    void testConvertToFoodCategoryEntity() {
        FoodCategoryRequest request = new FoodCategoryRequest();
        request.setRestaurantId(1L);
        request.setCategoryName("Test Category");

        FoodCategory category = dtoConversion.convertToFoodCategoryEntity(request);

        assertNotNull(category);
        assertEquals(request.getRestaurantId(), category.getRestaurantId());
        assertEquals(request.getCategoryName(), category.getCategoryName());
    }

    @Test
    void testConvertToFoodCategoryResponse() {
        FoodCategory category = new FoodCategory();
        category.setCategoryId(1L);
        category.setRestaurantId(1L);
        category.setCategoryName("Test Category");

        FoodCategoryResponse response = dtoConversion.convertToFoodCategoryResponse(category);

        assertNotNull(response);
        assertEquals(category.getCategoryId(), response.getCategoryId());
        assertEquals(category.getRestaurantId(), response.getRestaurantId());
        assertEquals(category.getCategoryName(), response.getCategoryName());
    }

    @Test
    void testConvertToRestaurantMenuEntity() {
        RestaurantMenuRequest request = new RestaurantMenuRequest();
        request.setItemName("Test Item");
        request.setPrice(10.0);
        request.setDescription("Test Description");
        request.setVegNonVeg(Boolean.TRUE);
        request.setCategoryId(1L);
        request.setRestaurantId(1L);

        RestaurantMenu menu = dtoConversion.convertToRestaurantMenuEntity(request);

        assertNotNull(menu);
        assertEquals(request.getItemName(), menu.getItemName());
        assertEquals(request.getPrice(), menu.getPrice());
        assertEquals(request.getDescription(), menu.getDescription());
        assertEquals(request.getVegNonVeg(), menu.getVegNonVeg());
        assertEquals(request.getCategoryId(), menu.getCategoryId());
        assertEquals(request.getRestaurantId(), menu.getRestaurantId());
    }


}
