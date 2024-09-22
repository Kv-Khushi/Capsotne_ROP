package com.restaurants.dto;

import com.restaurants.dto.FoodCategoryResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FoodCategoryResponseTest {

    @Test
    public void testCategoryId() {
        FoodCategoryResponse response = new FoodCategoryResponse();
        response.setCategoryId(1L);

        assertEquals(1L, response.getCategoryId());
    }

    @Test
    public void testRestaurantId() {
        FoodCategoryResponse response = new FoodCategoryResponse();
        response.setRestaurantId(100L);

        assertEquals(100L, response.getRestaurantId());
    }

    @Test
    public void testCategoryName() {
        FoodCategoryResponse response = new FoodCategoryResponse();
        response.setCategoryName("Sample Category");

        assertEquals("Sample Category", response.getCategoryName());
    }

    @Test
    public void testAllFields() {
        FoodCategoryResponse response = new FoodCategoryResponse();
        response.setCategoryId(2L);
        response.setRestaurantId(200L);
        response.setCategoryName("Sample Category");

        assertEquals(2L, response.getCategoryId());
        assertEquals(200L, response.getRestaurantId());
        assertEquals("Sample Category", response.getCategoryName());
    }
}

