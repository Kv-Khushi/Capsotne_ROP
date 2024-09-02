package com.restaurants.service;

import com.restaurants.constant.ConstantMessage;
import com.restaurants.dtoconversion.DtoConversion;
import com.restaurants.entities.Restaurant;
import com.restaurants.exception.NotFoundException;
import com.restaurants.indto.RestaurantRequest;
import com.restaurants.outdto.RestaurantResponse;
import com.restaurants.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private DtoConversion dtoConversion;

    @InjectMocks
    private RestaurantService restaurantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


//    @Test
//    void getRestaurantByIdTest() throws NotFoundException {
//        Long restaurantId = 1L;
//        Restaurant restaurant = new Restaurant();
//        restaurant.setRestaurantId(restaurantId);
//        restaurant.setRestaurantName("Test Restaurant");
//
//        RestaurantResponse restaurantResponse = new RestaurantResponse();
//        restaurantResponse.setRestaurantName("Test Restaurant");
//
//        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
//        when(dtoConversion.convertToRestaurantResponse(any(Restaurant.class))).thenReturn(restaurantResponse);
//
//        RestaurantResponse response = restaurantService.getRestaurantById(restaurantId);
//
//        assertNotNull(response);
//        assertEquals("Test Restaurant", response.getRestaurantName());
//
//        verify(restaurantRepository, times(1)).findById(restaurantId);
//        verify(dtoConversion, times(1)).convertToRestaurantResponse(any(Restaurant.class));
//    }

    @Test
    void getRestaurantByIdNotFoundTest() {
        Long restaurantId = 1L;

        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            restaurantService.getRestaurantById(restaurantId);
        });

        assertEquals(ConstantMessage.RESTAURANT_NOT_FOUND, thrown.getMessage());

        verify(restaurantRepository, times(1)).findById(restaurantId);
    }

    @Test
    void getRestaurantImageTest() throws NotFoundException {
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(restaurantId);
        restaurant.setRestaurantImage(new byte[]{1, 2, 3, 4, 5}); // Example image data

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        byte[] image = restaurantService.getRestaurantImage(restaurantId);

        assertNotNull(image);
        assertArrayEquals(new byte[]{1, 2, 3, 4, 5}, image);

        verify(restaurantRepository, times(1)).findById(restaurantId);
    }

    @Test
    void getRestaurantImageNotFoundTest() {
        Long restaurantId = 1L;

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            restaurantService.getRestaurantImage(restaurantId);
        });

        assertEquals(ConstantMessage.RESTAURANT_NOT_FOUND, thrown.getMessage());

        verify(restaurantRepository, times(1)).findById(restaurantId);
    }

}
