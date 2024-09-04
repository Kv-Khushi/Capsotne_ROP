package com.restaurants.controller;

import com.restaurants.exception.NotFoundException;
import com.restaurants.dto.indto.RestaurantRequest;
import com.restaurants.dto.outdto.RestaurantResponse;
import com.restaurants.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RestaurantControllerTest {

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantController restaurantController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void addRestaurantSuccessTest() throws Exception {
//        RestaurantRequest restaurantRequest = new RestaurantRequest();
//        restaurantRequest.setUserId(1L);
//        restaurantRequest.setRestaurantName("Test Restaurant");
//        restaurantRequest.setRestaurantAddress("123 Test St");
//        restaurantRequest.setContactNumber(1234567890L);
//        restaurantRequest.setRestaurantDescription("A test restaurant");
//        restaurantRequest.setOpeningHour("10");
//
//        MultipartFile multipartFile = mock(MultipartFile.class);
//        when(multipartFile.isEmpty()).thenReturn(false);
//        when(multipartFile.getBytes()).thenReturn(new byte[0]);
//
//        RestaurantResponse restaurantResponse = new RestaurantResponse();
//        restaurantResponse.setUserId(1L);
//        restaurantResponse.setRestaurantName("Test Restaurant");
//        restaurantResponse.setRestaurantAddress("123 Test St");
//        restaurantResponse.setContactNumber(1234567890L);
//        restaurantResponse.setRestaurantDescription("A test restaurant");
//        restaurantResponse.setOpeningHour("10");
//
//        when(restaurantService.addRestaurant(any(RestaurantRequest.class), any(MultipartFile.class)))
//                .thenReturn(restaurantResponse);
//
//        ResponseEntity<RestaurantResponse> responseEntity = restaurantController.addRestaurant(restaurantRequest, multipartFile);
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());
//        assertEquals("Test Restaurant", responseEntity.getBody().getRestaurantName());
//        verify(restaurantService, times(1)).addRestaurant(any(RestaurantRequest.class), any(MultipartFile.class));
//    }

    @Test
    void getAllRestaurantsSuccessTest() {
        List<RestaurantResponse> restaurantResponses = new ArrayList<>();
        RestaurantResponse restaurantResponse = new RestaurantResponse();
        restaurantResponse.setRestaurantName("Test Restaurant");
        restaurantResponses.add(restaurantResponse);

        when(restaurantService.getAllRestaurants()).thenReturn(restaurantResponses);

        ResponseEntity<List<RestaurantResponse>> responseEntity = restaurantController.getAllRestaurants();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals("Test Restaurant", responseEntity.getBody().get(0).getRestaurantName());
        verify(restaurantService, times(1)).getAllRestaurants();
    }

    @Test
    void getRestaurantByIdSuccessTest() throws NotFoundException {
        Long restaurantId = 1L;
        RestaurantResponse restaurantResponse = new RestaurantResponse();
        restaurantResponse.setRestaurantName("Test Restaurant");

        when(restaurantService.getRestaurantById(anyLong())).thenReturn(restaurantResponse);

        RestaurantResponse response = restaurantController.getRestaurantById(restaurantId);

        assertNotNull(response);
        assertEquals("Test Restaurant", response.getRestaurantName());
        verify(restaurantService, times(1)).getRestaurantById(restaurantId);
    }


    @Test
    void getRestaurantByIdNotFoundTest() throws NotFoundException {
        Long restaurantId = 1L;
        when(restaurantService.getRestaurantById(anyLong())).thenThrow(new NotFoundException("Restaurant not found"));

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            restaurantController.getRestaurantById(restaurantId);
        });

        assertEquals("Restaurant not found", thrown.getMessage());
        verify(restaurantService, times(1)).getRestaurantById(restaurantId);
    }

    @Test
    void getRestaurantImageSuccessTest() throws NotFoundException {
        Long restaurantId = 1L;
        byte[] imageData = new byte[]{1, 2, 3};

        when(restaurantService.getRestaurantImage(anyLong())).thenReturn(imageData);

        ResponseEntity<byte[]> responseEntity = restaurantController.getRestaurantImage(restaurantId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertArrayEquals(imageData, responseEntity.getBody());
        assertEquals("image/jpeg", responseEntity.getHeaders().getContentType().toString());
        verify(restaurantService, times(1)).getRestaurantImage(restaurantId);
    }

    @Test
    void getRestaurantImageNotFoundTest() throws NotFoundException {
        Long restaurantId = 1L;
        when(restaurantService.getRestaurantImage(anyLong())).thenThrow(new NotFoundException("Image not found"));

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            restaurantController.getRestaurantImage(restaurantId);
        });

        assertEquals("Image not found", thrown.getMessage());
        verify(restaurantService, times(1)).getRestaurantImage(restaurantId);
    }

    @Test
    void getAllRestaurantByUserIdSuccessTest() {
        Long userId = 1L;
        List<RestaurantResponse> restaurantResponses = new ArrayList<>();
        RestaurantResponse restaurantResponse = new RestaurantResponse();
        restaurantResponse.setRestaurantName("Test Restaurant");
        restaurantResponses.add(restaurantResponse);

        when(restaurantService.getALlRestaurantsByUserId(anyLong())).thenReturn(restaurantResponses);

        ResponseEntity<List<RestaurantResponse>> responseEntity = restaurantController.getAllRestaurantByUserId(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals("Test Restaurant", responseEntity.getBody().get(0).getRestaurantName());
        verify(restaurantService, times(1)).getALlRestaurantsByUserId(userId);
    }
}
