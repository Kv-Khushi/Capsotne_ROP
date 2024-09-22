package com.restaurants.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantResponseTest {

    @Test
    void testDefaultConstructor() {
        RestaurantResponse response = new RestaurantResponse();
        assertNull(response.getRestaurantId());
        assertNull(response.getUserId());
        assertNull(response.getRestaurantName());
        assertNull(response.getRestaurantAddress());
        assertNull(response.getContactNumber());
        assertNull(response.getRestaurantDescription());
        assertNull(response.getOpeningHour());
        assertNull(response.getRestaurantImage());
    }

    @Test
    void testParameterizedConstructor() {
        RestaurantResponse response = new RestaurantResponse();
        response.setRestaurantId(1L);
        response.setUserId(2L);
        response.setRestaurantName("Sample Name");
        response.setRestaurantAddress("123 Main St");
        response.setContactNumber("7834567890");
        response.setRestaurantDescription("Description of the restaurant");
        response.setOpeningHour("9 AM - 10 PM");

        byte[] imageBytes = new byte[]{1, 2, 3};
        response.setRestaurantImage(imageBytes);

        assertEquals(1L, response.getRestaurantId());
        assertEquals(2L, response.getUserId());
        assertEquals("Sample Name", response.getRestaurantName());
        assertEquals("123 Main St", response.getRestaurantAddress());
        assertEquals("7834567890", response.getContactNumber());
        assertEquals("Description of the restaurant", response.getRestaurantDescription());
        assertEquals("9 AM - 10 PM", response.getOpeningHour());
        assertArrayEquals(imageBytes, response.getRestaurantImage());
    }

    @Test
    void testLombokGettersAndSetters() {
        RestaurantResponse response = new RestaurantResponse();
        response.setRestaurantId(2L);
        response.setUserId(3L);
        response.setRestaurantName("Sample Name");
        response.setRestaurantAddress("456 Main road");
        response.setContactNumber("9876543210");
        response.setRestaurantDescription("Description of the restaurant");
        response.setOpeningHour("8 AM - 8 PM");

        byte[] imageBytes = new byte[]{4, 5, 6};
        response.setRestaurantImage(imageBytes);

        assertEquals(2L, response.getRestaurantId());
        assertEquals(3L, response.getUserId());
        assertEquals("Sample Name", response.getRestaurantName());
        assertEquals("456 Main road", response.getRestaurantAddress());
        assertEquals("9876543210", response.getContactNumber());
        assertEquals("Description of the restaurant", response.getRestaurantDescription());
        assertEquals("8 AM - 8 PM", response.getOpeningHour());
        assertArrayEquals(imageBytes, response.getRestaurantImage());
    }

    @Test
    void testRestaurantImageHandling() {
        RestaurantResponse response = new RestaurantResponse();
        byte[] imageBytes = new byte[]{7, 8, 9};
        response.setRestaurantImage(imageBytes);

        byte[] retrievedImageBytes = response.getRestaurantImage();
        assertNotNull(retrievedImageBytes);
        assertArrayEquals(imageBytes, retrievedImageBytes);

        // Modify the original array
        imageBytes[0] = 0;
        // Ensure that the retrieved array is not affected
        assertEquals(7, response.getRestaurantImage()[0]);
    }

    @Test
    void testLombokEqualsAndHashCode() {
        RestaurantResponse response1 = new RestaurantResponse();
        response1.setRestaurantId(1L);
        response1.setUserId(2L);
        response1.setRestaurantName("Sample Name");
        response1.setRestaurantAddress("123 Main St");
        response1.setContactNumber("7834567890");
        response1.setRestaurantDescription("Description of the restaurant");
        response1.setOpeningHour("9 AM - 10 PM");

        byte[] imageBytes1 = new byte[]{1, 2, 3};
        response1.setRestaurantImage(imageBytes1);

        RestaurantResponse response2 = new RestaurantResponse();
        response2.setRestaurantId(1L);
        response2.setUserId(2L);
        response2.setRestaurantName("Sample Name");
        response2.setRestaurantAddress("123 Main St");
        response2.setContactNumber("7834567890");
        response2.setRestaurantDescription("Description of the restaurant");
        response2.setOpeningHour("9 AM - 10 PM");

        byte[] imageBytes2 = new byte[]{1, 2, 3};
        response2.setRestaurantImage(imageBytes2);

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void testLombokToString() {
        RestaurantResponse response = new RestaurantResponse();
        response.setRestaurantId(2L);
        response.setUserId(3L);
        response.setRestaurantName("Sample Name");
        response.setRestaurantAddress("456 Main road");
        response.setContactNumber("9876543210");
        response.setRestaurantDescription("Description of the restaurant");
        response.setOpeningHour("8 AM - 8 PM");

        byte[] imageBytes = new byte[]{4, 5, 6};
        response.setRestaurantImage(imageBytes);

        String expectedString = "RestaurantResponse(restaurantId=2, userId=3, restaurantName=Sample Name, restaurantAddress=456 Main road, contactNumber=9876543210, restaurantDescription=Description of the restaurant, openingHour=8 AM - 8 PM, restaurantImage=[B@"+Integer.toHexString(imageBytes.hashCode())+")";
        assertTrue(response.toString().contains("RestaurantResponse"));
    }
}

