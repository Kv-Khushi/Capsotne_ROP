package com.restaurants.controller;

import com.restaurants.constant.ConstantMessage;
import com.restaurants.dto.RestaurantRequest;
import com.restaurants.dto.SuccessResponse;
import com.restaurants.exception.InvalidRequestException;
import com.restaurants.exception.ResourceNotFoundException;
import com.restaurants.dto.RestaurantResponse;
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

    @Test
    void addRestaurantSuccessTest() {
        RestaurantRequest request = new RestaurantRequest();
        request.setUserId(1L);
        request.setRestaurantName("Test Restaurant");
        request.setRestaurantAddress("Test Address");
        request.setContactNumber("9876543210");
        request.setRestaurantDescription("Test Description");
        request.setOpeningHour("10:00 AM");

        // Create a mock MultipartFile
        MultipartFile image = mock(MultipartFile.class);
        when(image.getOriginalFilename()).thenReturn("image.jpg");
        when(image.getContentType()).thenReturn("image/jpeg");

        when(restaurantService.addRestaurant(any(RestaurantRequest.class), any(MultipartFile.class)))
                .thenReturn(new RestaurantResponse());

        ResponseEntity<SuccessResponse> responseEntity = restaurantController.addRestaurant(request, image);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(ConstantMessage.RESTAURANT_ADD_SUCCESS, responseEntity.getBody().getMessage());
        verify(restaurantService, times(1)).addRestaurant(any(RestaurantRequest.class), any(MultipartFile.class));
    }



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
    void getRestaurantByIdSuccessTest() throws ResourceNotFoundException {
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
    void getRestaurantByIdInvalidIdTest() throws ResourceNotFoundException {
        Long restaurantId = 999L;
        when(restaurantService.getRestaurantById(anyLong()))
                .thenThrow(new ResourceNotFoundException("Restaurant not found"));

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            restaurantController.getRestaurantById(restaurantId);
        });

        assertEquals("Restaurant not found", thrown.getMessage());
        verify(restaurantService, times(1)).getRestaurantById(restaurantId);
    }

    @Test
    void getRestaurantByIdNotFoundTest() throws ResourceNotFoundException {
        Long restaurantId = 1L;
        when(restaurantService.getRestaurantById(anyLong())).thenThrow(new ResourceNotFoundException("Restaurant not found"));

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            restaurantController.getRestaurantById(restaurantId);
        });

        assertEquals("Restaurant not found", thrown.getMessage());
        verify(restaurantService, times(1)).getRestaurantById(restaurantId);
    }

    @Test
    void getRestaurantImageSuccessTest() throws ResourceNotFoundException {
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
    void getRestaurantImageNotFoundTest() throws ResourceNotFoundException {
        Long restaurantId = 1L;
        when(restaurantService.getRestaurantImage(anyLong())).thenThrow(new ResourceNotFoundException("Image not found"));

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
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

    @Test
    void getAllRestaurantByUserIdEmptyListTest() {
        Long userId = 1L;
        when(restaurantService.getALlRestaurantsByUserId(anyLong())).thenReturn(new ArrayList<>());

        ResponseEntity<List<RestaurantResponse>> responseEntity = restaurantController.getAllRestaurantByUserId(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(restaurantService, times(1)).getALlRestaurantsByUserId(userId);
    }

    @Test
    void addRestaurantMissingFieldsTest() {
        RestaurantRequest request = new RestaurantRequest();
        request.setUserId(1L);

        // Create a mock MultipartFile
        MultipartFile image = mock(MultipartFile.class);

        when(restaurantService.addRestaurant(any(RestaurantRequest.class), any(MultipartFile.class)))
                .thenThrow(new InvalidRequestException("Missing required fields"));

        InvalidRequestException thrown = assertThrows(InvalidRequestException.class, () -> {
            restaurantController.addRestaurant(request, image);
        });

        assertEquals("Missing required fields", thrown.getMessage());
        verify(restaurantService, times(1)).addRestaurant(any(RestaurantRequest.class), any(MultipartFile.class));
    }

}
