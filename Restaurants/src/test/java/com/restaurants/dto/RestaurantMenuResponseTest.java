package com.restaurants.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantMenuResponseTest {

    @Test
    public void testDefaultConstructor() {
        RestaurantMenuResponse response = new RestaurantMenuResponse();
        assertNull(response.getItemId());
        assertNull(response.getItemName());
        assertNull(response.getPrice());
        assertNull(response.getDescription());
        assertNull(response.getVegNonVeg());
        assertNull(response.getCategoryId());
        assertNull(response.getRestaurantId());
        assertNull(response.getImageUrl());
    }

    @Test
    public void testAllArgsConstructor() {
        byte[] image = new byte[]{1, 2, 3, 4};
        RestaurantMenuResponse response = new RestaurantMenuResponse();
        response.setItemId(1L);
        response.setItemName("Burger");
        response.setPrice(5.99);
        response.setDescription("Delicious beef burger");
        response.setVegNonVeg(false);
        response.setCategoryId(10L);
        response.setRestaurantId(100L);
        response.setImageUrl(image);

        assertEquals(1L, response.getItemId());
        assertEquals("Burger", response.getItemName());
        assertEquals(5.99, response.getPrice());
        assertEquals("Delicious beef burger", response.getDescription());
        assertFalse(response.getVegNonVeg());
        assertEquals(10L, response.getCategoryId());
        assertEquals(100L, response.getRestaurantId());
        assertArrayEquals(image, response.getImageUrl());
    }

    @Test
    public void testGetImageUrlClone() {
        byte[] image = new byte[]{1, 2, 3, 4};
        RestaurantMenuResponse response = new RestaurantMenuResponse();
        response.setImageUrl(image);

        byte[] clonedImage = response.getImageUrl();
        clonedImage[0] = 9;

        // Ensure that modifying the cloned array doesn't affect the original
        assertEquals(1, image[0]);  // Check that the original array remains unchanged
    }

    @Test
    public void testSetImageUrlClone() {
        byte[] image = new byte[]{1, 2, 3, 4};
        RestaurantMenuResponse response = new RestaurantMenuResponse();
        response.setImageUrl(image);

        byte[] newImage = new byte[]{5, 6, 7, 8};
        response.setImageUrl(newImage);

        // Ensure that the internal imageUrl array has been updated
        assertArrayEquals(newImage, response.getImageUrl());
    }

    @Test
    public void testSetImageUrlNull() {
        RestaurantMenuResponse response = new RestaurantMenuResponse();
        response.setImageUrl(null);

        assertNull(response.getImageUrl());
    }
}
