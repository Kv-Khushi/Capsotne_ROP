package com.restaurants.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodCategoryTest {

    @Test
    void testSetGetCategoryId() {
        FoodCategory foodCategory = new FoodCategory();
        Long id = 1L;
        foodCategory.setCategoryId(id);
        assertEquals(id, foodCategory.getCategoryId(), "Category ID should match");
    }

    @Test
    void testSetGetRestaurantId() {
        FoodCategory foodCategory = new FoodCategory();
        Long restaurantId = 2L;
        foodCategory.setRestaurantId(restaurantId);
        assertEquals(restaurantId, foodCategory.getRestaurantId(), "Restaurant ID should match");
    }

    @Test
    void testSetGetCategoryName() {
        FoodCategory foodCategory = new FoodCategory();
        String name = " Sample Category";
        foodCategory.setCategoryName(name);
        assertEquals(name, foodCategory.getCategoryName(), "Category name should match");
    }

    @Test
    void testEqualsAndHashCode() {
        FoodCategory foodCategory1 = new FoodCategory();
        foodCategory1.setCategoryId(1L);
        foodCategory1.setRestaurantId(2L);
        foodCategory1.setCategoryName("Sample Category");

        FoodCategory foodCategory2 = new FoodCategory();
        foodCategory2.setCategoryId(1L);
        foodCategory2.setRestaurantId(2L);
        foodCategory2.setCategoryName("Sample Category");

        // Test equality
        assertEquals(foodCategory1, foodCategory2, "FoodCategory objects should be equal");

        // Test hashCode
        assertEquals(foodCategory1.hashCode(), foodCategory2.hashCode(), "Hash codes should be equal");
    }

    @Test
    void testToString() {
        FoodCategory foodCategory = new FoodCategory();
        foodCategory.setCategoryId(1L);
        foodCategory.setRestaurantId(2L);
        foodCategory.setCategoryName("Sample Category");

        String expectedToString = "FoodCategory(categoryId=1, restaurantId=2, categoryName=Sample Category)";
        assertEquals(expectedToString, foodCategory.toString(), "toString() output should match expected format");
    }
}
