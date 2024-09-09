package com.restaurants.indto;

import com.restaurants.dto.outdto.RestaurantResponse;
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
        response.setRestaurantName("The Bistro");
        response.setRestaurantAddress("123 Main St");
        response.setContactNumber(1234567890L);
        response.setRestaurantDescription("A cozy place with a great menu");
        response.setOpeningHour("9 AM - 10 PM");

        byte[] imageBytes = new byte[]{1, 2, 3};
        response.setRestaurantImage(imageBytes);

        assertEquals(1L, response.getRestaurantId());
        assertEquals(2L, response.getUserId());
        assertEquals("The Bistro", response.getRestaurantName());
        assertEquals("123 Main St", response.getRestaurantAddress());
        assertEquals(1234567890L, response.getContactNumber());
        assertEquals("A cozy place with a great menu", response.getRestaurantDescription());
        assertEquals("9 AM - 10 PM", response.getOpeningHour());
        assertArrayEquals(imageBytes, response.getRestaurantImage());
    }

    @Test
    void testLombokGettersAndSetters() {
        RestaurantResponse response = new RestaurantResponse();
        response.setRestaurantId(2L);
        response.setUserId(3L);
        response.setRestaurantName("Cafe Delight");
        response.setRestaurantAddress("456 Elm St");
        response.setContactNumber(9876543210L);
        response.setRestaurantDescription("Great coffee and pastries");
        response.setOpeningHour("8 AM - 8 PM");

        byte[] imageBytes = new byte[]{4, 5, 6};
        response.setRestaurantImage(imageBytes);

        assertEquals(2L, response.getRestaurantId());
        assertEquals(3L, response.getUserId());
        assertEquals("Cafe Delight", response.getRestaurantName());
        assertEquals("456 Elm St", response.getRestaurantAddress());
        assertEquals(9876543210L, response.getContactNumber());
        assertEquals("Great coffee and pastries", response.getRestaurantDescription());
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
        response1.setRestaurantName("The Bistro");
        response1.setRestaurantAddress("123 Main St");
        response1.setContactNumber(1234567890L);
        response1.setRestaurantDescription("A cozy place with a great menu");
        response1.setOpeningHour("9 AM - 10 PM");

        byte[] imageBytes1 = new byte[]{1, 2, 3};
        response1.setRestaurantImage(imageBytes1);

        RestaurantResponse response2 = new RestaurantResponse();
        response2.setRestaurantId(1L);
        response2.setUserId(2L);
        response2.setRestaurantName("The Bistro");
        response2.setRestaurantAddress("123 Main St");
        response2.setContactNumber(1234567890L);
        response2.setRestaurantDescription("A cozy place with a great menu");
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
        response.setRestaurantName("Cafe Delight");
        response.setRestaurantAddress("456 Elm St");
        response.setContactNumber(9876543210L);
        response.setRestaurantDescription("Great coffee and pastries");
        response.setOpeningHour("8 AM - 8 PM");

        byte[] imageBytes = new byte[]{4, 5, 6};
        response.setRestaurantImage(imageBytes);

        String expectedString = "RestaurantResponse(restaurantId=2, userId=3, restaurantName=Cafe Delight, restaurantAddress=456 Elm St, contactNumber=9876543210, restaurantDescription=Great coffee and pastries, openingHour=8 AM - 8 PM, restaurantImage=[B@"+Integer.toHexString(imageBytes.hashCode())+")";
        assertTrue(response.toString().contains("RestaurantResponse"));
    }
}
