package com.restaurants.indto;


import com.restaurants.dto.indto.FoodCategoryRequest;
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
        request.setCategoryName("Appetizers");

        assertEquals(1L, request.getRestaurantId());
        assertEquals("Appetizers", request.getCategoryName());
    }

    @Test
    void testLombokGettersAndSetters() {
        FoodCategoryRequest request = new FoodCategoryRequest();
        request.setRestaurantId(2L);
        request.setCategoryName("Main Course");

        assertEquals(2L, request.getRestaurantId());
        assertEquals("Main Course", request.getCategoryName());
    }

    @Test
    void testLombokEqualsAndHashCode() {
        FoodCategoryRequest request1 = new FoodCategoryRequest();
        request1.setRestaurantId(3L);
        request1.setCategoryName("Desserts");

        FoodCategoryRequest request2 = new FoodCategoryRequest();
        request2.setRestaurantId(3L);
        request2.setCategoryName("Desserts");

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void testLombokToString() {
        FoodCategoryRequest request = new FoodCategoryRequest();
        request.setRestaurantId(4L);
        request.setCategoryName("Beverages");

        String expectedString = "FoodCategoryRequest(restaurantId=4, categoryName=Beverages)";
        assertTrue(request.toString().contains("FoodCategoryRequest"));
    }
}
