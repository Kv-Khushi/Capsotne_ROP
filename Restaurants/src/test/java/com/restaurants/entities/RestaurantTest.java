package com.restaurants.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    @Test
    void testSetGetRestaurantId() {
        Restaurant restaurant = new Restaurant();
        Long id = 1L;
        restaurant.setRestaurantId(id);
        assertEquals(id, restaurant.getRestaurantId(), "Restaurant ID should match");
    }

    @Test
    void testSetGetUserId() {
        Restaurant restaurant = new Restaurant();
        Long userId = 1L;
        restaurant.setUserId(userId);
        assertEquals(userId, restaurant.getUserId(), "User ID should match");
    }

    @Test
    void testSetGetRestaurantName() {
        Restaurant restaurant = new Restaurant();
        String name = "Test Restaurant";
        restaurant.setRestaurantName(name);
        assertEquals(name, restaurant.getRestaurantName(), "Restaurant name should match");
    }

    @Test
    void testSetGetRestaurantAddress() {
        Restaurant restaurant = new Restaurant();
        String address = "123 Test Street";
        restaurant.setRestaurantAddress(address);
        assertEquals(address, restaurant.getRestaurantAddress(), "Restaurant address should match");
    }

    @Test
    void testSetGetContactNumber() {
        Restaurant restaurant = new Restaurant();
        String contactNumber =" 7894567890";
        restaurant.setContactNumber(contactNumber);
        assertEquals(contactNumber, restaurant.getContactNumber(), "Contact number should match");
    }

    @Test
    void testSetGetRestaurantDescription() {
        Restaurant restaurant = new Restaurant();
        String description = "A sample description of restaurant.";
        restaurant.setRestaurantDescription(description);
        assertEquals(description, restaurant.getRestaurantDescription(), "Restaurant description should match");
    }

    @Test
    void testSetGetOpeningHour() {
        Restaurant restaurant = new Restaurant();
        String openingHour = "09:00 AM - 09:00 PM";
        restaurant.setOpeningHour(openingHour);
        assertEquals(openingHour, restaurant.getOpeningHour(), "Opening hour should match");
    }

    @Test
    void testSetGetRestaurantImage() {
        Restaurant restaurant = new Restaurant();
        byte[] image = new byte[] {1, 2, 3};
        restaurant.setRestaurantImage(image);
        assertArrayEquals(image, restaurant.getRestaurantImage(), "Restaurant image should match");
    }

    @Test
    void testSetRestaurantImageWithNull() {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantImage(null);
        assertNull(restaurant.getRestaurantImage(), "Restaurant image should be null");
    }

//    @Test
//    void testGetRestaurantImageReturnsClone() {
//        Restaurant restaurant = new Restaurant();
//        byte[] originalImage = new byte[] {1, 2, 3};
//        restaurant.setRestaurantImage(originalImage);
//        byte[] retrievedImage = restaurant.getRestaurantImage();
//
//        // Modify the retrieved image to ensure the original is not affected
//        retrievedImage[0] = 99;
//
//
//
//    }
}
