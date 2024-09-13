package com.restaurants.service;

import com.restaurants.constant.ConstantMessage;
import com.restaurants.dto.RestaurantResponse;
import com.restaurants.dtoconversion.DtoConversion;
import com.restaurants.entities.Restaurant;
import com.restaurants.exception.NotFoundException;
import com.restaurants.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void getAllRestaurants_Success() {
        List<Restaurant> restaurants = Arrays.asList(new Restaurant(), new Restaurant());
        List<RestaurantResponse> responses = Arrays.asList(new RestaurantResponse(), new RestaurantResponse());

        when(restaurantRepository.findAll()).thenReturn(restaurants);
        when(dtoConversion.convertToRestaurantResponse(any(Restaurant.class))).thenReturn(responses.get(0), responses.get(1));

        List<RestaurantResponse> result = restaurantService.getAllRestaurants();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(restaurantRepository, times(1)).findAll();
        verify(dtoConversion, times(2)).convertToRestaurantResponse(any(Restaurant.class));
    }
}