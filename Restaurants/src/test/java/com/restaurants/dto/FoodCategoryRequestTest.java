package com.restaurants.dto;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FoodCategoryRequestTest {

    @Test
    void testDefaultConstructor() {
        FoodCategoryRequest request = new FoodCategoryRequest();
        assertNull(request.getRestaurantId());
        assertNull(request.getCategoryName());
    }

    @Test
    void testParameterizedConstructor() {
        FoodCategoryRequest request = new FoodCategoryRequest();
        request.setRestaurantId(1L);
        request.setCategoryName("Sample Category");

        assertEquals(1L, request.getRestaurantId());
        assertEquals("Sample Category", request.getCategoryName());
    }

    @Test
    void testLombokGettersAndSetters() {
        FoodCategoryRequest request = new FoodCategoryRequest();
        request.setRestaurantId(2L);
        request.setCategoryName("Sample Category");

        assertEquals(2L, request.getRestaurantId());
        assertEquals("Sample Category", request.getCategoryName());
    }

    @Test
    void testLombokEqualsAndHashCode() {
        FoodCategoryRequest request1 = new FoodCategoryRequest();
        request1.setRestaurantId(3L);
        request1.setCategoryName("Sample Category");

        FoodCategoryRequest request2 = new FoodCategoryRequest();
        request2.setRestaurantId(3L);
        request2.setCategoryName("Sample Category");

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void testLombokToString() {
        FoodCategoryRequest request = new FoodCategoryRequest();
        request.setRestaurantId(4L);
        request.setCategoryName("Sample Category");

        String expectedString = "FoodCategoryRequest(restaurantId=4, categoryName=Sample Category)";
        assertTrue(request.toString().contains("FoodCategoryRequest"));
    }
}
