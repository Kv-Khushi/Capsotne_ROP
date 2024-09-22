package com.restaurants.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantMenuTest {

    @Test
    void testGetImageUrl_WhenImageUrlIsNull() {
        RestaurantMenu menu = new RestaurantMenu();
        assertNull(menu.getImageUrl(), "Image URL should be null");
    }

    @Test
    void testGetImageUrl_WhenImageUrlIsNotNull() {
        byte[] originalImage = {1, 2, 3, 4, 5};
        RestaurantMenu menu = new RestaurantMenu();
        menu.setImageUrl(originalImage);
        byte[] retrievedImage = menu.getImageUrl();
        assertNotNull(retrievedImage, "Image URL should not be null");
        assertArrayEquals(originalImage, retrievedImage, "Image URL should be equal to the original image");

    }

    @Test
    void testSetImageUrl() {
        byte[] newImage = {5, 4, 3, 2, 1};
        RestaurantMenu menu = new RestaurantMenu();
        menu.setImageUrl(newImage);
        byte[] retrievedImage = menu.getImageUrl();
        assertNotNull(retrievedImage, "Image URL should not be null");
        assertArrayEquals(newImage, retrievedImage, "Image URL should be equal to the new image");
    }

    @Test
    void testSetImageUrl_WithNull() {
        RestaurantMenu menu = new RestaurantMenu();
        menu.setImageUrl(null);
        assertNull(menu.getImageUrl(), "Image URL should be null after setting it to null");
    }
}
