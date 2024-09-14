package com.restaurants.dto;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantMenuRequestTest {

    @Test
    void testDefaultConstructor() {
        RestaurantMenuRequest request = new RestaurantMenuRequest();
        assertNull(request.getItemName());
        assertNull(request.getPrice());
        assertNull(request.getDescription());
        assertNull(request.getVegNonVeg());
        assertNull(request.getCategoryId());
        assertNull(request.getRestaurantId());
        assertNull(request.getImageUrl());
    }

    @Test
    void testParameterizedConstructor() {
        RestaurantMenuRequest request = new RestaurantMenuRequest();
        request.setItemName("Burger");
        request.setPrice(10.99);
        request.setDescription("Delicious beef burger");
        request.setVegNonVeg(false);
        request.setCategoryId(1L);
        request.setRestaurantId(2L);

        // Mocking MultipartFile
        MultipartFile mockFile = new MockMultipartFile("image", "burger.jpg", "image/jpeg", new byte[0]);
        request.setImageUrl(mockFile);

        assertEquals("Burger", request.getItemName());
        assertEquals(10.99, request.getPrice());
        assertEquals("Delicious beef burger", request.getDescription());
        assertFalse(request.getVegNonVeg());
        assertEquals(1L, request.getCategoryId());
        assertEquals(2L, request.getRestaurantId());
        assertNotNull(request.getImageUrl());
        assertEquals("image/jpeg", request.getImageUrl().getContentType());
    }

    @Test
    void testLombokGettersAndSetters() {
        RestaurantMenuRequest request = new RestaurantMenuRequest();
        request.setItemName("Pasta");
        request.setPrice(12.99);
        request.setDescription("Creamy Alfredo Pasta");
        request.setVegNonVeg(true);
        request.setCategoryId(3L);
        request.setRestaurantId(4L);

        MultipartFile mockFile = new MockMultipartFile("image", "pasta.jpg", "image/jpeg", new byte[0]);
        request.setImageUrl(mockFile);

        assertEquals("Pasta", request.getItemName());
        assertEquals(12.99, request.getPrice());
        assertEquals("Creamy Alfredo Pasta", request.getDescription());
        assertTrue(request.getVegNonVeg());
        assertEquals(3L, request.getCategoryId());
        assertEquals(4L, request.getRestaurantId());
        assertNotNull(request.getImageUrl());
        assertEquals("image/jpeg", request.getImageUrl().getContentType());
    }

    @Test
    void testLombokEqualsAndHashCode() {
        RestaurantMenuRequest request1 = new RestaurantMenuRequest();
        request1.setItemName("Salad");
        request1.setPrice(8.99);
        request1.setDescription("Fresh garden salad");
        request1.setVegNonVeg(true);
        request1.setCategoryId(5L);
        request1.setRestaurantId(6L);

        RestaurantMenuRequest request2 = new RestaurantMenuRequest();
        request2.setItemName("Salad");
        request2.setPrice(8.99);
        request2.setDescription("Fresh garden salad");
        request2.setVegNonVeg(true);
        request2.setCategoryId(5L);
        request2.setRestaurantId(6L);

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void testLombokToString() {
        RestaurantMenuRequest request = new RestaurantMenuRequest();
        request.setItemName("Pizza");
        request.setPrice(14.99);
        request.setDescription("Cheesy pizza");
        request.setVegNonVeg(false);
        request.setCategoryId(7L);
        request.setRestaurantId(8L);

        MultipartFile mockFile = new MockMultipartFile("image", "pizza.jpg", "image/jpeg", new byte[0]);
        request.setImageUrl(mockFile);

        String expectedString = "RestaurantMenuRequest(itemName=Pizza, price=14.99, description=Cheesy pizza, vegNonVeg=false, categoryId=7, restaurantId=8, imageUrl=pizza.jpg)";
        assertTrue(request.toString().contains("RestaurantMenuRequest"));
    }
}
